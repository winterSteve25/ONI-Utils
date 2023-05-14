package com.github.wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseInvTE;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIHasValidItems;

import javax.annotation.Nonnull;
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
        if (!(tile instanceof ONIIHasValidItems validItems)) {
            return true;
        }
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!(tile instanceof ONIIHasValidItems validItems)) {
            return super.insertItem(slot, stack, simulate);
        }
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
    }
}
