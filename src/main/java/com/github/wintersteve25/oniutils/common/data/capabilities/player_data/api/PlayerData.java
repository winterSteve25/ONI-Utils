package com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.HashMap;
import java.util.Map;

public class PlayerData implements IPlayerData {

    private Map<SkillType, Integer> skillLevels;
    private int morale;
    private int buildMoraleBonus;
    private int temperature;

    public PlayerData() {
        skillLevels = new HashMap<>();

        for (var skill : SkillType.values()) {
            skillLevels.put(skill, 0);
        }

        morale = 100;
        buildMoraleBonus = 0;
        temperature = 37;
    }

    @Override
    public Map<SkillType, Integer> getSkills() {
        return skillLevels;
    }

    @Override
    public void setSkills(Map<SkillType, Integer> skills) {
        this.skillLevels = skills;
    }

    @Override
    public void setLevel(SkillType skill, int level) {
        skillLevels.remove(skill);
        skillLevels.put(skill, level);
    }

    @Override
    public int getMorale() {
        return morale + buildMoraleBonus;
    }

    @Override
    public int getMoraleRaw() {
        return morale;
    }

    @Override
    public void setMorale(int in) {
        morale = in;
    }

    @Override
    public int getBuildMoraleBonus() {
        return buildMoraleBonus;
    }

    @Override
    public void setBuildMoraleBonus(int in) {
        buildMoraleBonus = in;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int in) {
        temperature = in;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        ListTag skillsKeys = new ListTag();
        ListTag skillsValues = new ListTag();

        for (var type : skillLevels.keySet()) {
            skillsKeys.add(StringTag.valueOf(type.name()));
            skillsValues.add(IntTag.valueOf(skillLevels.get(type)));
        }

        nbt.put("skillsKeys", skillsKeys);
        nbt.put("skillsValues", skillsValues);

        nbt.putInt("morale", getMorale());
        nbt.putInt("buildMoraleBonus", getBuildMoraleBonus());
        nbt.putInt("temperature", getTemperature());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.get("skillsKeys") instanceof ListTag keys && nbt.get("skillsValues") instanceof ListTag values) {
            skillLevels = new HashMap<>();
            for (var i = 0; i < keys.size(); i++) {
                var skill = keys.get(i);
                if (values.get(i) instanceof IntTag level) {
                    skillLevels.put(SkillType.valueOf(skill.getAsString()), level.getAsInt());
                }
            }
        }

        setMorale(nbt.getInt("morale"));
        setBuildMoraleBonus(nbt.getInt("buildMoraleBonus"));
        setTemperature(nbt.getInt("temperature"));
    }
}
