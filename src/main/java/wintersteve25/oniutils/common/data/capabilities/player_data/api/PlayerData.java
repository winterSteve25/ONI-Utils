package wintersteve25.oniutils.common.data.capabilities.player_data.api;

import net.minecraft.nbt.CompoundTag;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.ArrayList;
import java.util.List;

public class PlayerData implements IPlayerData {

    private int morale = 100;
    private int buildMoraleBonus = 0;
    private int temperature = 37;

    public PlayerData() {

    }

    @Override
    public int getMorale() {
        return morale+buildMoraleBonus;
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
    public int getBuildBonus() {
        return buildMoraleBonus;
    }

    @Override
    public void setBuildBonus(int in) {
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
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }
}
