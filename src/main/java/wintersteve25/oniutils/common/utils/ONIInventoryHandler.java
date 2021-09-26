package wintersteve25.oniutils.common.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasValidItems;

import javax.annotation.Nonnull;
import java.util.List;

public class ONIInventoryHandler extends ItemStackHandler {
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
