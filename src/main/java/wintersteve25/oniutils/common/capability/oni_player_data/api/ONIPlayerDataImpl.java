package wintersteve25.oniutils.common.capability.oni_player_data.api;

import wintersteve25.oniutils.common.utils.MiscHelper;

import java.util.ArrayList;
import java.util.List;

public class ONIPlayerDataImpl implements ONIIPlayerData {

    private int randomTrait = MiscHelper.randomInRange(TraitTypes.getBottom(), TraitTypes.getTop());
    private int goodTrait = MiscHelper.randomInRange(TraitTypes.getGoodBottom(), TraitTypes.getGoodTop());
    private int badTrait = MiscHelper.randomInRange(TraitTypes.getBadBottom(), TraitTypes.getBadTop());
    private final List<Integer> traitList = new ArrayList<>();
    private boolean gotTraitBonus = false;
    private int morale = 100;
    private int buildMoraleBonus = 0;
    private int temperature = 37;

    public ONIPlayerDataImpl() {
        while (randomTrait == goodTrait || randomTrait == badTrait) {
            randomTrait = MiscHelper.randomInRange(TraitTypes.getBottom(), TraitTypes.getTop());
        }
        traitList.add(0, randomTrait);
        traitList.add(1, goodTrait);
        traitList.add(2, badTrait);
    }

    @Override
    public void setTrait(int randTrait, int goodTrait, int badTrait) {
        this.randomTrait = randTrait;
        this.goodTrait = goodTrait;
        this.badTrait = badTrait;

        traitList.clear();
        traitList.add(0, this.randomTrait);
        traitList.add(1, this.goodTrait);
        traitList.add(2, this.badTrait);
    }

    @Override
    public List<Integer> getTraits() {
        return this.traitList;
    }

    @Override
    public void setGottenTrait(boolean set) {
        this.gotTraitBonus = set;
    }

    @Override
    public boolean getGottenTrait() {
        return this.gotTraitBonus;
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
}
