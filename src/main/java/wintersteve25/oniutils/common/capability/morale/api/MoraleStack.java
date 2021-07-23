package wintersteve25.oniutils.common.capability.morale.api;

public class MoraleStack implements IMorale {
    private int morale = 50;

    @Override
    public int getMorale() {
        return morale;
    }

    @Override
    public void setMorale(int amount) {
        this.morale = amount;
    }

    @Override
    public void addMorale(int amount) {
        morale += amount;
    }

    @Override
    public void removeMorale(int amount) {
        morale -= amount;
    }
}
