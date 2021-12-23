package wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.common.contents.base.ONIBaseInvTE;
import wintersteve25.oniutils.api.ONIIHasValidItems;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.function.BiPredicate;

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
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!(tile instanceof ONIIHasValidItems)) {
            return super.insertItem(slot, stack, simulate);
        }
        ONIIHasValidItems validItems = (ONIIHasValidItems) tile;
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
    }
}
