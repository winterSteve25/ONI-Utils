package wintersteve25.oniutils.api;

import net.minecraft.item.ItemStack;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationContext;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationHandler;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import wintersteve25.oniutils.common.utils.helpers.ONIModInventoryHandler;

/**
 * Should be implemented on a {@link wintersteve25.oniutils.common.contents.base.ONIBaseInvTE}
 */
public interface ONIIModifiable {
    ModificationContext modContext();

    ModificationHandler modHandler();

    default boolean addMod(ONIBaseTE te, ItemStack stack) {
        ONIModInventoryHandler handler = modContext().getModHandler();
        for(int i = 0; i < modContext().getMaxModAmount(); i++) {
            if(handler.getStackInSlot(i).isEmpty()) {
                if (stack.getItem() instanceof ONIModificationItem) {
                    ONIModificationItem modification = (ONIModificationItem) stack.getItem();
                    if (modContext().isModValid(modification.getModType())) {
                        handler.insertItem(i, stack.copy(), false);
                        stack.shrink(1);
                        te.updateBlock();
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
