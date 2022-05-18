package wintersteve25.oniutils.common.data.capabilities.player_data.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public interface IPlayerData extends INBTSerializable<CompoundTag> {
    Map<SkillType, Integer> getSkills();
    void setSkills(Map<SkillType, Integer> skills);
    void setLevel(SkillType skill, int level);

    int getMorale();
    int getMoraleRaw();
    void setMorale(int in);

    int getBuildMoraleBonus();
    void setBuildMoraleBonus(int in);

    int getTemperature();
    void setTemperature(int in);
}
