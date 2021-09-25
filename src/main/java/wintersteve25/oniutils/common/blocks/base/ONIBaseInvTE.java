package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasValidItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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
        if (isServer()) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }

        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (isServer()) {
            tag.put("inv", itemHandler.serializeNBT());
        }

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

    public static class ONIInventoryHandler extends ItemStackHandler {
        private final ONIBaseInvTE tile;

        public ONIInventoryHandler(ONIBaseInvTE inv) {
            super(inv.getInvSize());
            tile = inv;
        }

        @Override
        public void onContentsChanged(int slot) {
            tile.markDirty();
            tile.updateBlock();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (!(tile instanceof ONIIHasValidItems)) {
                return true;
            }
            ONIIHasValidItems validItems = (ONIIHasValidItems) tile;
            List<Item> valids = validItems.validItems();
            return valids == null || valids.isEmpty() || valids.contains(stack.getItem());
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!(tile instanceof ONIIHasValidItems)) {
                return super.insertItem(slot, stack, simulate);
            }
            ONIIHasValidItems validItems = (ONIIHasValidItems) tile;
            List<Item> valids = validItems.validItems();
            if (valids == null || valids.isEmpty()) {
                return super.insertItem(slot, stack, simulate);
            }
            if (!valids.contains(stack.getItem())) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    }
}
