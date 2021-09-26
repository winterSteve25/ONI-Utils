package wintersteve25.oniutils.common.blocks.modules.power.coal;

import com.google.common.collect.Lists;
import mekanism.common.tile.interfaces.IBoundingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasProgress;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasValidItems;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIModifiable;
import wintersteve25.oniutils.common.capability.durability.DurabilityCapability;
import wintersteve25.oniutils.common.capability.durability.api.DurabilityStack;
import wintersteve25.oniutils.common.capability.durability.api.IDurability;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.items.base.enums.EnumModifications;
import wintersteve25.oniutils.common.items.modules.modifications.ModificationContext;
import wintersteve25.oniutils.common.utils.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CoalGenTE extends ONIBaseInvTE implements ITickableTileEntity, IAnimatable, IBoundingBlock, ONIIHasProgress, ONIIHasRedstoneOutput, ONIIHasValidItems, ONIIModifiable {

    public final ModificationContext modificationContext = new ModificationContext(this, 12, EnumModifications.values());
    private final AnimationFactory manager = new AnimationFactory(this);
    private final PlasmaStack plasmaHandler = new PlasmaStack(4000, EnumWattsTypes.LOW);
    private final LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);
    private final DurabilityStack durabilityStack = new DurabilityStack();
    private final LazyOptional<IDurability> durabilityLazyOptional = LazyOptional.of(() -> durabilityStack);
    private boolean removedFirstItem = false;

    private int progress = 0;
    private int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
    private boolean isForceStopped = false;
    private boolean isInverted = false;
    private boolean isWorking = false;

    private int lowThreshold = 20;
    private int highThreshold = 80;

    public CoalGenTE() {
        super(ONIBlocks.COAL_GEN_TE.get());
    }

    @Override
    public void tick() {
        super.tick();

        if (isServer()) {
            if (getForceStopped()) {
                setWorking(false);
            }

            if (!getForceStopped()) {
                if (progress > 0) {
                    if (plasmaHandler.canGenerate(ONIConfig.COAL_GEN_PLASMA_OUTPUT.get())) {
                        if (!removedFirstItem) {
                            itemHandler.extractItem(0, 1, false);
                            removedFirstItem = true;
                        }
                        progress--;
                        plasmaHandler.addPower(ONIConfig.COAL_GEN_PLASMA_OUTPUT.get());
                        setWorking(true);
                        if (progress <= 0) {
                            removedFirstItem = false;
                            setWorking(false);
                            progress = 0;
                        }
                    }
                } else {
                    if (plasmaHandler.canGenerate(ONIConfig.COAL_GEN_PLASMA_OUTPUT.get())) {
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

            if (!plasmaHandler.canGenerate(ONIConfig.COAL_GEN_PLASMA_OUTPUT.get())) {
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
    public List<Item> validItems() {
        return Lists.newArrayList(Items.COAL);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return modificationContext.getCombinedLazyOptional().cast();
        }
        if (cap == PlasmaCapability.POWER_CAPABILITY) {
            return powerLazyOptional.cast();
        }
        if (cap == DurabilityCapability.DURABILITY_CAPABILITY) {
            return durabilityLazyOptional.cast();
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
}
