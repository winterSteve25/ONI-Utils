package wintersteve25.oniutils.common.blocks.base.interfaces;

import net.minecraft.item.ItemStack;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.items.modules.modifications.ModificationContext;
import wintersteve25.oniutils.common.items.modules.modifications.ModificationHandler;
import wintersteve25.oniutils.common.items.modules.modifications.ONIBaseModification;
import wintersteve25.oniutils.common.utils.ONIModInventoryHandler;

/**
 * Should be implemented in a {@link wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE}
 */
public interface ONIIModifiable {
    ModificationContext modContext();

    ModificationHandler modHandler();

    default boolean addMod(ONIBaseTE te, ItemStack stack) {
        ONIModInventoryHandler handler = modContext().getModHandler();
        for(int i = 0; i < modContext().getMaxModAmount(); i++) {
            if(handler.getStackInSlot(i).isEmpty()) {
                if (stack.getItem() instanceof ONIBaseModification) {
                    ONIBaseModification modification = (ONIBaseModification) stack.getItem();
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
