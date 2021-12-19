package wintersteve25.oniutils.api;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.function.BiPredicate;

/**
 * Should be implemented on a {@link wintersteve25.oniutils.common.contents.base.ONIBaseInvTE}
 */
public interface ONIIHasValidItems {
    default HashMap<Item, Integer> validItems() {
        return null;
    }

    default BiPredicate<Item, Integer> validItemsPredicate() {
        return null;
    }
}
