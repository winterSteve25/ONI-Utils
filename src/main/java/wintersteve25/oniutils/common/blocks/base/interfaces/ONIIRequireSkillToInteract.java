package wintersteve25.oniutils.common.blocks.base.interfaces;

import wintersteve25.oniutils.common.items.modules.modifications.ModificationHandler;

import java.util.HashMap;

/**
 * Should be implemented in a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIRequireSkillToInteract {
    HashMap<String, Integer> requiredSkill();

    ModificationHandler modHandler();

    default int getRequiredLevel(String skill) {
        int levelReq = requiredSkill().get(skill);
        if (modHandler() == null) return levelReq;
        return modHandler().getRequiredSkillLevel(levelReq);
    }
}
