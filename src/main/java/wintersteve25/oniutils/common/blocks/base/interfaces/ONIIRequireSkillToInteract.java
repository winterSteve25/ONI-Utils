package wintersteve25.oniutils.common.blocks.base.interfaces;

import wintersteve25.oniutils.common.items.modules.modifications.ModificationHandler;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIRequireSkillToInteract {
    String requiredSkill();

    ModificationHandler modHandler();

    @Deprecated
    int baseRequiredLevel();

    default int getRequiredLevel() {
        return modHandler().getRequiredSkillLevel(baseRequiredLevel());
    }
}
