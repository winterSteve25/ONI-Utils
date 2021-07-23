package wintersteve25.oniutils.common.blocks.modules.power.coal;

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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.EnumWattsTypes;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CoalGenTE extends ONIBaseInvTE implements ITickableTileEntity, IAnimatable, IBoundingBlock {

    private final AnimationFactory manager = new AnimationFactory(this);
    private final PlasmaStack plasmaHandler = new PlasmaStack(4000, EnumWattsTypes.LOW);
    private final LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);
    private final List<Item> valids = new ArrayList<>();

    private boolean removedFirstItem = false;

    @Override
    protected int totalProgress() {
        return ONIConfig.COAL_GEN_PROCESS_TIME.get();
    }

    public CoalGenTE() {
        super(ONIBlocks.COAL_GEN_TE.get());
        valids.add(Items.COAL);
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isRemote()) {
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
                            progress = totalProgress();
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

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("plasma", plasmaHandler.write());

        return super.write(tag);
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public List<Item> validItems() {
        return valids;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == PlasmaCapability.POWER_CAPABILITY) {
            return powerLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemLazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 80;
        if (super.getWorking()) {
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
                    this.world.removeBlock(this.getPos(), false);
                    break;
                case SOUTH:
                    this.world.removeBlock(this.getPos().west(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().west(), false);
                    this.world.removeBlock(this.getPos(), false);
                    break;
                case EAST:
                    this.world.removeBlock(this.getPos().south(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().south(), false);
                    this.world.removeBlock(this.getPos(), false);
                    break;
                case WEST:
                    this.world.removeBlock(this.getPos().north(), false);
                    this.world.removeBlock(this.getPos().up(), false);
                    this.world.removeBlock(this.getPos().up().north(), false);
                    this.world.removeBlock(this.getPos(), false);
                    break;
            }
        }
    }
}
