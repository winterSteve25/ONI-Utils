package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class ONIBaseInvTE extends ONIBaseTE {

    protected ItemStackHandler itemHandler = new ONIInventoryHandler(this);
    protected LazyOptional<IItemHandler> itemLazyOptional = LazyOptional.of(() -> itemHandler);

    public ONIBaseInvTE(TileEntityType<?> te) {
        super(te);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public abstract int getInvSize();

    public abstract List<Item> validItems();

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

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(world.getBlockState(pos),pkt.getNbtCompound());
    }

    public void updateBlock(){
        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 2);
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
            if (tile.validItems() == null) {
                return true;
            }
            return tile.validItems().contains(stack.getItem());
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (tile.validItems() == null) {
                return super.insertItem(slot, stack, simulate);
            }
            if (!tile.validItems().contains(stack.getItem())) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    }
}
