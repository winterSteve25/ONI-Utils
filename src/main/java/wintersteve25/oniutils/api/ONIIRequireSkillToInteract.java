package wintersteve25.oniutils.api;

import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationHandler;

import java.util.HashMap;

/**
 * Should be implemented on a {@link net.minecraft.tileentity.TileEntity}
 */
public interface ONIIRequireSkillToInteract extends ONIIModifiable {
    HashMap<String, Integer> requiredSkill();

    ModificationHandler modHandler();

    default int getRequiredLevel(String skill) {
        int levelReq = requiredSkill().get(skill);
        if (modHandler() == null) return levelReq;
        return modHandler().getRequiredSkillLevel(levelReq);
    }
}
