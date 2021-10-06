package wintersteve25.oniutils.common.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasValidItems;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

public class ONIInventoryHandler extends ItemStackHandler {
    private final ONIBaseInvTE tile;

    public ONIInventoryHandler(ONIBaseInvTE inv) {
        super(inv.getInvSize());
        tile = inv;
    }

    @Override
    public void onContentsChanged(int slot) {
        tile.updateBlock();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (!(tile instanceof ONIIHasValidItems)) {
            return true;
        }
        ONIIHasValidItems validItems = (ONIIHasValidItems) tile;
        HashMap<Item, Integer> valids = validItems.validItems();
        if (valids == null || valids.isEmpty()) return true;
        if (valids.containsKey(stack.getItem())) {
            return slot == valids.get(stack.getItem()) || valids.get(stack.getItem()) < 0;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!(tile instanceof ONIIHasValidItems)) {
            return super.insertItem(slot, stack, simulate);
        }
        ONIIHasValidItems validItems = (ONIIHasValidItems) tile;
        HashMap<Item, Integer> valids = validItems.validItems();
        if (valids == null || valids.isEmpty()) {
            return super.insertItem(slot, stack, simulate);
        }
        if (!valids.containsKey(stack.getItem())) {
            return stack;
        }
        if (valids.get(stack.getItem()) != slot || valids.get(stack.getItem()) != -1) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
