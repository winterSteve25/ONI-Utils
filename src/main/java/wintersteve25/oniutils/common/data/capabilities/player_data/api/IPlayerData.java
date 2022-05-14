package wintersteve25.oniutils.common.data.capabilities.player_data.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IPlayerData extends INBTSerializable<CompoundTag> {
    void setTrait(int randTrait, int goodTrait, int badTrait);

    List<Integer> getTraits();

    void setGottenTrait(boolean set);

    boolean getGottenTrait();

    int getMorale();

    int getMoraleRaw();

    void setMorale(int in);

    int getBuildBonus();

    void setBuildBonus(int in);

    int getTemperature();

    void setTemperature(int in);
}
