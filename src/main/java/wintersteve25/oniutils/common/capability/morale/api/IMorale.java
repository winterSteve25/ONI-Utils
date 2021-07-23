package wintersteve25.oniutils.common.capability.morale.api;

public interface IMorale {
    int getMorale();

    void setMorale(int amount);

    void addMorale(int amount);

    void removeMorale(int amount);
}
