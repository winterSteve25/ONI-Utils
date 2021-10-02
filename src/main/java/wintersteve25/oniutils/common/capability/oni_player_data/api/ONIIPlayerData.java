package wintersteve25.oniutils.common.capability.oni_player_data.api;

import java.util.List;

public interface ONIIPlayerData {
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
