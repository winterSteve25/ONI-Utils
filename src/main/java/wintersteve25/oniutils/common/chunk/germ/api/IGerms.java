package wintersteve25.oniutils.common.chunk.germ.api;

public interface IGerms {
    void addGerm(EnumGermTypes germType, int amount);

    void setGerm(EnumGermTypes germType, int amount);

    EnumGermTypes getGermType();

    int getGermAmount();
}
