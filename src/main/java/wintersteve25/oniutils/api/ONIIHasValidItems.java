package wintersteve25.oniutils.api;

import net.minecraft.world.item.ItemStack;

import java.util.function.BiPredicate;

/**
 * Should be implemented on a {@link wintersteve25.oniutils.common.contents.base.ONIBaseInvTE}
 */
public interface ONIIHasValidItems {
    BiPredicate<ItemStack, Integer> validItemsPredicate();
}
