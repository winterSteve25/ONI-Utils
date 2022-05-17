package wintersteve25.oniutils.common.contents.modules.blocks.power.coal;

import com.google.common.collect.Lists;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.contents.base.interfaces.*;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.EnumPlasmaTileType;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.IPlasma;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.Plasma;
import wintersteve25.oniutils.common.contents.base.blocks.ONIBaseInvTE;
import wintersteve25.oniutils.common.contents.base.blocks.ONIBaseLoggableMachine;
import wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIIBoundingBlock;
import wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;
import wintersteve25.oniutils.common.contents.base.builders.ONIContainerBuilder;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationContext;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationHandler;
import wintersteve25.oniutils.common.registries.ONIBlocks;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.registries.ONIConfig;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.SlotArrangement;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;

import static wintersteve25.oniutils.common.utils.helpers.MiscHelper.ONEPIXEL;

public class CoalGenTE extends ONIBaseInvTE implements IAnimatable, ONIITickableServer, ONIIBoundingBlock, ONIIHasProgress, ONIIForceStoppable, ONIIHasRedstoneOutput, ONIIHasValidItems, ONIIMachine, ONIIRequireSkillToInteract {

    public final ModificationContext modificationContext = new ModificationContext(this, 9, EnumModifications.SPEED, EnumModifications.TEMPERATURE, EnumModifications.COMPLEXITY);
    private final ModificationHandler modificationHandler = new ModificationHandler(modificationContext);
    private final AnimationFactory manager = new AnimationFactory(this);
    private final Plasma plasmaHandler = new Plasma(4000, EnumPlasmaTileType.PRODUCER);
    private final LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);
    private boolean removedFirstItem = false;

    private int progress = 0;
    private int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
    private boolean isForceStopped = false;
    private boolean isInverted = false;
    private boolean isWorking = false;

    private int lowThreshold = 20;
    private int highThreshold = 80;

    public CoalGenTE(BlockPos pos, BlockState state) {
        super(ONIBlocks.Machines.Power.COAL_GEN_TE.get(), pos, state);
    }

    @Override
    public void serverTick() {
        if (getLevel() != null) {
            if (getForceStopped()) {
                setWorking(false);
            }

            int plasmaEachTick = producingPower();

            if (!getForceStopped()) {
                if (progress > 0) {
                    int progressSpeed = modificationHandler.getProgressSpeed();
                    if (plasmaHandler.canGenerate(plasmaEachTick)) {
                        if (!removedFirstItem) {
                            itemHandler.extractItem(0, 1, false);
                            removedFirstItem = true;
                        }
                        progress-=progressSpeed;
                        plasmaHandler.addPower(plasmaEachTick);
                        setWorking(true);
                        if (progress <= 0) {
                            removedFirstItem = false;
                            setWorking(false);
                            progress = 0;
                        }
                    }
                } else {
                    if (plasmaHandler.canGenerate(plasmaEachTick)) {
                        if (!itemHandler.getStackInSlot(0).isEmpty()) {
                            progress = getTotalProgress();
                            setWorking(true);
                        } else {
                            setWorking(false);
                            progress = 0;
                        }
                    }
                }
            }

            if (!plasmaHandler.canGenerate(plasmaEachTick)) {
                removedFirstItem = false;
                setWorking(false);
                progress = 0;
            }

            setChanged();
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        plasmaHandler.deserializeNBT(tag.getCompound("plasma"));
        modificationContext.deserializeNBT(tag.getCompound("modifications"));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("plasma", plasmaHandler.serializeNBT());
        tag.put("modifications", modificationContext.serializeNBT());
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public BiPredicate<ItemStack, Integer> validItemsPredicate() {
        return (stack, slot) -> stack.getItem() == Items.COAL;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side != null) {
                return itemLazyOptional.cast();
            }
            return modificationContext.getCombinedLazyOptional().cast();
        }
        if (cap == ONICapabilities.PLASMA) {
            return powerLazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 80;
        if (getWorking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.motor.new", true));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder());
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

    @Override
    public void onPlace() {
        Direction facing = getBlockState().getValue(BlockStateProperties.FACING);

        switch (facing) {
            case NORTH:
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().east(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above().east(), this.getBlockPos());
                break;
            case SOUTH:
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().west(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above().west(), this.getBlockPos());
                break;
            case EAST:
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().south(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above().south(), this.getBlockPos());
                break;
            case WEST:
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().north(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above(), this.getBlockPos());
                MiscHelper.makeBoundingBlock(this.getLevel(), this.getBlockPos().above().north(), this.getBlockPos());
                break;
        }
    }

    @Override
    public void onBreak(BlockState oldState) {
        if (this.level != null) {
            Direction facing = getBlockState().getValue(BlockStateProperties.FACING);

            switch (facing) {
                case NORTH:
                    this.level.removeBlock(this.getBlockPos().east(), false);
                    this.level.removeBlock(this.getBlockPos().above(), false);
                    this.level.removeBlock(this.getBlockPos().above().east(), false);
                    break;
                case SOUTH:
                    this.level.removeBlock(this.getBlockPos().west(), false);
                    this.level.removeBlock(this.getBlockPos().above(), false);
                    this.level.removeBlock(this.getBlockPos().above().west(), false);
                    break;
                case EAST:
                    this.level.removeBlock(this.getBlockPos().south(), false);
                    this.level.removeBlock(this.getBlockPos().above(), false);
                    this.level.removeBlock(this.getBlockPos().above().south(), false);
                    break;
                case WEST:
                    this.level.removeBlock(this.getBlockPos().north(), false);
                    this.level.removeBlock(this.getBlockPos().above(), false);
                    this.level.removeBlock(this.getBlockPos().above().north(), false);
                    break;
            }
        }
    }

    @Override
    public int getProgress() {
        return progress;
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
        updateBlock();
    }

    @Override
    public int getTotalProgress() {
        return totalProgress;
    }

    @Override
    public void setTotalProgress(int progress) {
        this.totalProgress = progress;
        updateBlock();
    }

    @Override
    public boolean getForceStopped() {
        return isForceStopped;
    }

    @Override
    public void setForceStopped(boolean forceStopped) {
        this.isForceStopped = forceStopped;
        updateBlock();
    }

    @Override
    public boolean isInverted() {
        return isInverted;
    }

    @Override
    public void toggleInverted() {
        isInverted = !isInverted;
        updateBlock();
    }

    @Override
    public boolean getWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
        updateBlock();
    }

    @Override
    public int lowThreshold() {
        return lowThreshold;
    }

    @Override
    public int highThreshold() {
        return highThreshold;
    }

    @Override
    public void setLowThreshold(int in) {
        lowThreshold = in;
        updateBlock();
    }

    @Override
    public void setHighThreshold(int in) {
        highThreshold = in;
        updateBlock();
    }

    @Override
    public ModificationContext modContext() {
        return modificationContext;
    }

    @Override
    public HashMap<String, Integer> requiredSkill() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("machinery", 4);
        return map;
    }

    @Override
    public ModificationHandler modHandler() {
        return modificationHandler;
    }

    @Override
    public List<MachineType> machineType() {
        return Lists.newArrayList(MachineType.POWER_PRODUCER);
    }

    @Override
    public int producingPower() {
        return modificationHandler.getPlasmaOutputPerTick(getTotalProgress(), ONIConfig.COAL_GEN_POWER_PRODUCE.get());
    }

    private static final VoxelShape BOTTOM1 = Shapes.box(0D, 0, 0D, 1D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM2 = Shapes.box(1D, 0, 0D, 2D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM = Shapes.or(BOTTOM1, BOTTOM2);
    private static final VoxelShape SUPPORT1 = Shapes.box(ONEPIXEL*6, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, ONEPIXEL*6, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT2 = Shapes.box(2D-ONEPIXEL*3, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, 2D-ONEPIXEL*1, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT = Shapes.or(SUPPORT1, SUPPORT2);
    private static final VoxelShape MIDDLE = Shapes.box(ONEPIXEL*6, ONEPIXEL*6, ONEPIXEL*4, 2D-ONEPIXEL*3, 1+ONEPIXEL*9, 1D-ONEPIXEL*4);
    private static final VoxelShape REDSTONEPANEL = VoxelShapeUtils.rotate(Shapes.box(ONEPIXEL*4, ONEPIXEL, ONEPIXEL*14, ONEPIXEL*13, ONEPIXEL*13, 1D), Rotation.CLOCKWISE_90);
    private static final VoxelShape CONNECTION = VoxelShapeUtils.rotate(Shapes.box(ONEPIXEL*7, ONEPIXEL*7, ONEPIXEL*12, ONEPIXEL*10, ONEPIXEL*11, ONEPIXEL*14), Rotation.CLOCKWISE_90);

    public static final VoxelShape NORTH_R = Shapes.or(BOTTOM, SUPPORT, MIDDLE, CONNECTION, REDSTONEPANEL).optimize();

    public static ONIBlockBuilder<ONIBaseLoggableMachine<CoalGenTE>> createBlock() {
        return new ONIBlockBuilder<>(ONIConstants.LangKeys.COAL_GEN, () -> new ONIBaseLoggableMachine<>(BlockBehaviour.Properties.of(Material.METAL).strength(1.4F, 5).requiresCorrectToolForDrops().noOcclusion(), CoalGenTE.class, ONIBlocks.Machines.Power.COAL_GEN_TE), ONIConstants.Geo.COAL_GEN_ISTER, true)
                .placementCondition(ONIConstants.PlacementConditions::fourByFourCondition)
                .renderType((state)-> RenderShape.ENTITYBLOCK_ANIMATED)
                .autoRotateShape()
                .shape((state, world, pos, ctx)->NORTH_R)
                .container(new ONIIHasGui() {
                    @Override
                    public AbstractContainerMenu container(int i, Level world, BlockPos pos, Inventory playerInventory, Player playerEntity) {
                        return ONIBlocks.Machines.Power.COAL_GEN_CONTAINER_BUILDER.buildNewInstance(i, world, pos, playerInventory, playerEntity);
                    }

                    @Override
                    public Component machineName() {
                        return LangHelper.guiTitle(ONIConstants.LangKeys.COAL_GEN);
                    }
                })
                .coloredName(()->ONIConstants.TextColor.POWER_CAT_COLOR)
                .tooltip(LangHelper.itemTooltip(ONIConstants.LangKeys.COAL_GEN))
                .shiftToolTip()
                .noModelGen();
    }

    public static ONIContainerBuilder createContainer() {
        return new ONIContainerBuilder(ONIConstants.LangKeys.COAL_GEN)
                .trackPower()
                .trackPowerCapacity()
                .trackProgress()
                .trackWorking()
                .setInternalSlotArrangement(new SlotArrangement(55, 32))
                .addInternalInventory();
    }
}
