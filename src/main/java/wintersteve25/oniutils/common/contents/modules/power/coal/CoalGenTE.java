package wintersteve25.oniutils.common.contents.modules.power.coal;

import com.google.common.collect.Lists;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.VoxelShapeUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
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
import wintersteve25.oniutils.api.*;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.contents.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.contents.base.ONIBaseMachine;
import wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;
import wintersteve25.oniutils.common.contents.base.builders.ONIContainerBuilder;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.contents.modules.modifications.ModificationContext;
import wintersteve25.oniutils.common.contents.modules.modifications.ModificationHandler;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.SlotArrangement;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static wintersteve25.oniutils.common.utils.helpers.MiscHelper.ONEPIXEL;

public class CoalGenTE extends ONIBaseInvTE implements ITickableTileEntity, IAnimatable, IBoundingBlock, ONIIHasProgress, ONIIHasRedstoneOutput, ONIIHasValidItems, ONIIModifiable, ONIIMachine {

    public final ModificationContext modificationContext = new ModificationContext(this, 9, EnumModifications.SPEED, EnumModifications.TEMPERATURE, EnumModifications.COMPLEXITY);
    private final ModificationHandler modificationHandler = new ModificationHandler(modificationContext);
    private final AnimationFactory manager = new AnimationFactory(this);
    private final PlasmaStack plasmaHandler = new PlasmaStack(4000, EnumWattsTypes.LOW);
    private final LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);
    private boolean removedFirstItem = false;

    private int progress = 0;
    private int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
    private boolean isForceStopped = false;
    private boolean isInverted = false;
    private boolean isWorking = false;

    private int lowThreshold = 20;
    private int highThreshold = 80;

    public CoalGenTE() {
        super(ONIBlocks.Machines.Power.COAL_GEN_TE.get());
    }

    @Override
    public void tick() {
        super.tick();

        if (isServer()) {
            if (getForceStopped()) {
                setWorking(false);
            }

            int plasmaEachTick = modificationHandler.getPlasmaOutputPerTick(getTotalProgress(), ONIConfig.COAL_GEN_POWER_PRODUCE.get());

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

            markDirty();
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        plasmaHandler.read(tag.getCompound("plasma"));
        modificationContext.deserializeNBT(tag.getCompound("modifications"));

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("plasma", plasmaHandler.write());
        tag.put("modifications", modificationContext.serializeNBT());

        return super.write(tag);
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public HashMap<Item, Integer> validItems() {
        return MiscHelper.newHashmap(Arrays.asList(Items.COAL), Arrays.asList(0));
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
        if (cap == PlasmaCapability.POWER_CAPABILITY) {
            return powerLazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
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
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }

    @Override
    public void onPlace() {
        Direction facing = getBlockState().get(BlockStateProperties.FACING);

        switch (facing) {
            case NORTH:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().east(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().east(), this.getPos());
                break;
            case SOUTH:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().west(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().west(), this.getPos());
                break;
            case EAST:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().south(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().south(), this.getPos());
                break;
            case WEST:
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().north(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up(), this.getPos());
                MiscHelper.makeBoundingBlock(this.getWorld(), this.getPos().up().north(), this.getPos());
                break;
        }
    }

    @Override
    public void onBreak(BlockState oldState) {
        if (this.world != null) {
            Direction facing = getBlockState().get(BlockStateProperties.FACING);

            switch (facing) {
                case NORTH:
                    this.world.removeBlock(this.getPos().east(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().east(), false);
                    break;
                case SOUTH:
                    this.world.removeBlock(this.getPos().west(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().west(), false);
                    break;
                case EAST:
                    this.world.removeBlock(this.getPos().south(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().south(), false);
                    break;
                case WEST:
                    this.world.removeBlock(this.getPos().north(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().north(), false);
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
    }

    @Override
    public int getTotalProgress() {
        return totalProgress;
    }

    @Override
    public void setTotalProgress(int progress) {
        this.totalProgress = progress;
    }

    @Override
    public boolean getForceStopped() {
        return isForceStopped;
    }

    @Override
    public void setForceStopped(boolean forceStopped) {
        this.isForceStopped = forceStopped;
    }

    @Override
    public boolean isInverted() {
        return isInverted;
    }

    @Override
    public void toggleInverted() {
        isInverted = !isInverted;
    }

    @Override
    public boolean getWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
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

    private static final VoxelShape BOTTOM1 = VoxelShapes.create(0D, 0, 0D, 1D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM2 = VoxelShapes.create(1D, 0, 0D, 2D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM = VoxelShapes.or(BOTTOM1, BOTTOM2);
    private static final VoxelShape SUPPORT1 = VoxelShapes.create(ONEPIXEL*6, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, ONEPIXEL*6, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT2 = VoxelShapes.create(2D-ONEPIXEL*1, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, 2D-ONEPIXEL*3, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT = VoxelShapes.or(SUPPORT1, SUPPORT2);
    private static final VoxelShape MIDDLE = VoxelShapes.create(ONEPIXEL*6, ONEPIXEL*6, ONEPIXEL*4, 2D-ONEPIXEL*3, 1+ONEPIXEL*9, 1D-ONEPIXEL*4);
    private static final VoxelShape REDSTONEPANEL = VoxelShapeUtils.rotate(VoxelShapes.create(ONEPIXEL*4, ONEPIXEL, ONEPIXEL*14, ONEPIXEL*13, ONEPIXEL*13, 1D), Rotation.CLOCKWISE_90);
    private static final VoxelShape CONNECTION = VoxelShapeUtils.rotate(VoxelShapes.create(ONEPIXEL*7, ONEPIXEL*7, ONEPIXEL*12, ONEPIXEL*10, ONEPIXEL*11, ONEPIXEL*14), Rotation.CLOCKWISE_90);

    public static final VoxelShape NORTH_R = VoxelShapes.or(BOTTOM, SUPPORT, MIDDLE, CONNECTION, REDSTONEPANEL).simplify();

    public static ONIBlockBuilder<ONIBaseMachine> createBlock() {
        return new ONIBlockBuilder<>(() -> new ONIBaseMachine("Coal Generator", AbstractBlock.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.4F, 5).setRequiresTool().notSolid(), CoalGenTE.class), ONIConstants.Geo.COAL_GEN_ISTER, true)
                .placementCondition(CoalGenTE::placeCondition)
                .renderType((state)-> BlockRenderType.ENTITYBLOCK_ANIMATED)
                .autoRotateShape()
                .shape((state, world, pos, ctx)->NORTH_R)
                .tileEntity((state, world)->ONIBlocks.Machines.Power.COAL_GEN_TE.get())
                .container(new ONIIHasGui() {
                    @Override
                    public Container container(int i, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return ONIBlocks.Machines.Power.COAL_GEN_CONTAINER_BUILDER.build(i, world, pos, playerInventory, playerEntity);
                    }

                    @Override
                    public ITextComponent machineName() {
                        return LangHelper.guiTitle(ONIConstants.LangKeys.COAL_GEN);
                    }
                })
                .noModelGen()
                .coloredName(()->ONIConstants.TextColor.POWER_CAT_COLOR)
                .tooltip(()-> Collections.singletonList(LangHelper.itemTooltip(ONIConstants.LangKeys.COAL_GEN)))
                .shiftToolTip();
    }

    public static ONIContainerBuilder createContainer() {
        return new ONIContainerBuilder(ONIBlocks.Machines.Power.COAL_GEN_BLOCK.getRegName())
                .trackPower()
                .trackPowerCapacity()
                .trackProgress()
                .trackWorking()
                .setInternalSlotArrangement(new SlotArrangement(55, 32))
                .addInternalInventory();
    }

    private static boolean placeCondition(BlockItemUseContext context, BlockState state) {
        World world = context.getWorld();
        BlockPos ogPos = context.getPos();

        switch (state.get(BlockStateProperties.FACING)) {
            case NORTH:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.east())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.east().up())) {
                            return true;
                        }
                    }
                }
                break;
            case SOUTH:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.west())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.west().up())) {
                            return true;
                        }
                    }
                }
                break;
            case WEST:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.north())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.north().up())) {
                            return true;
                        }
                    }
                }
                break;
            case EAST:
                if (WorldUtils.isValidReplaceableBlock(world, ogPos.south())) {
                    if (WorldUtils.isValidReplaceableBlock(world, ogPos.up())) {
                        if (WorldUtils.isValidReplaceableBlock(world, ogPos.south().up())) {
                            return true;
                        }
                    }
                }
                break;
        }

        return false;
    }
}
