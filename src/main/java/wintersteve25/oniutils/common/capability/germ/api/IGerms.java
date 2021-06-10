package wintersteve25.oniutils.common.capability.germ.api;

public interface IGerms {
    void addGerm(EnumGermTypes germType, int amount);

    void setGerm(EnumGermTypes germType, int amount);

    void removeGerm(int amount);

    EnumGermTypes getGermType();

    int getGermAmount();
}
