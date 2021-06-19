package wintersteve25.oniutils.common.capability.trait.api;

import java.util.List;

public interface ITrait {
    void setTrait(int randTrait, int goodTrait, int badTrait);

    List<Integer> getTraits();

    void setGottenTrait(boolean set);

    boolean getGottenTrait();
}
