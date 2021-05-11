package wintersteve25.oniutils.common.chunk.germ.world.api;

import wintersteve25.oniutils.common.chunk.germ.world.GermData;

public interface IGermData {
    GermData createGerm(int amount);

    GermData getGermData();

    void setGermType(GermData germType);

    int getGermColor();

    void setGermColor(int color);

    String getGermName();

    void setGermName(String name);

    int getGermAmount();

    void setGermAmount(int amount);

    GermData getGermByName(String name);
}
