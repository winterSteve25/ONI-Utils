package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseTE;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.plasma.api.IPlasma;
import wintersteve25.oniutils.common.capability.plasma.api.PlasmaStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoalGenTE extends ONIBaseTE implements ITickableTileEntity, IAnimatable {

    private final AnimationFactory manager = new AnimationFactory(this);
    private ItemStackHandler itemHandler = new ItemStackHandler(){
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL || stack.getItem() == Items.COAL_BLOCK) {
                return true;
            }

            return false;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stack.getItem() != Items.COAL || stack.getItem() != Items.CHARCOAL || stack.getItem() != Items.COAL_BLOCK) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private PlasmaStack plasmaHandler = new PlasmaStack();

    private LazyOptional<IItemHandler> itemLazyOptional = LazyOptional.of(() -> itemHandler);
    private LazyOptional<IPlasma> powerLazyOptional = LazyOptional.of(() -> plasmaHandler);

    private boolean isWorking = false;
    private int progress = ONIConfig.COAL_GEN_PROCESS_TIME.get();

    public CoalGenTE() {
        super(ONIBlocks.COAL_GEN_TE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (!itemHandler.getStackInSlot(0).isEmpty()) {
                isWorking = true;
                progress--;
                if (progress <= 0) {
                    plasmaHandler.addPower(ONIConfig.COAL_GEN_PLASMA_OUTPUT.get());
                    progress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
                    return;
                }
            }
        }

        isWorking = false;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT tag) {
        plasmaHandler.read(tag.getCompound("plasma"));
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        super.load(p_230337_1_, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("plasma", plasmaHandler.write());
        tag.put("inv", itemHandler.serializeNBT());

        return super.save(tag);
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
        event.getController().transitionLengthTicks = 0;
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.motor.new", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}
