package com.github.wintersteve25.oniutils.common.contents.base.interfaces;

import net.minecraft.world.item.ItemStack;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseInvTE;

import java.util.function.BiPredicate;

/**
 * Should be implemented on a {@link ONIBaseInvTE}
 */
public interface ONIIHasValidItems {
    BiPredicate<ItemStack, Integer> validItemsPredicate();
}
