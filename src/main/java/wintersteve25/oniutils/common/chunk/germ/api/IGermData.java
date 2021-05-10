package wintersteve25.oniutils.common.chunk.germ.api;

import wintersteve25.oniutils.common.chunk.germ.GermData;

public interface IGermData {
    GermData createGerm(int amount);

    GermData getGermType();

    void setGermType(GermData germType);

    int getGermColor();

    void setGermColor(int color);

    String getGermName();

    void setGermName(String name);

    int getGermAmount();

    void setGermAmount(int amount);

    GermData getGermByName(String name);
}
