package wintersteve25.oniutils.common.contents.base;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.common.utils.helpers.ONIInventoryHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ONIBaseInvTE extends ONIBaseTE {

    protected final ItemStackHandler itemHandler = new ONIInventoryHandler(this);
    protected final LazyOptional<IItemHandler> itemLazyOptional = LazyOptional.of(() -> itemHandler);

    public ONIBaseInvTE(TileEntityType<?> te) {
        super(te);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
    
    public abstract int getInvSize();

    public boolean hasItem() {
        for (int i = 0; i < getInvSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());

        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
