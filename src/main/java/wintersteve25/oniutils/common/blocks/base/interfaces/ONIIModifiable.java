package wintersteve25.oniutils.common.blocks.base.interfaces;

import wintersteve25.oniutils.common.items.modules.modifications.ModificationContext;
import wintersteve25.oniutils.common.items.modules.modifications.ModificationHandler;

/**
 * Should be implemented in a {@link wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE}
 */
public interface ONIIModifiable {
    ModificationContext modContext();

    ModificationHandler modHandler();
}
