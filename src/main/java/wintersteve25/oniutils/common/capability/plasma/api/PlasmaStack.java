package wintersteve25.oniutils.common.capability.plasma.api;

import net.minecraft.nbt.CompoundNBT;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;

public class PlasmaStack implements IPlasma {

    private int power = 0;
    private int capacity = 1000;
    private EnumWattsTypes watts = EnumWattsTypes.LOW;

    public PlasmaStack() {
    }

    public PlasmaStack(int capacity, EnumWattsTypes watts) {
        this.capacity = capacity;
        this.watts = watts;
    }

    @Override
    public void addPower(int power) {
        if (canGenerate(power)) {
            this.power += power;
        }
    }

    @Override
    public void removePower(int power) {
        if (canExtract(power)) {
            this.power -= power;
        }
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public boolean canGenerate(int power) {
        int newPower = this.power+power;
        return newPower <= capacity;
    }

    @Override
    public boolean canExtract(int power) {
        int newPower = this.power-power;

        return newPower > 0;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setWatts(EnumWattsTypes watts) {
        this.watts = watts;
    }

    @Override
    public EnumWattsTypes getWatts() {
        return watts;
    }

    @Override
    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("power", power);
        nbt.putString("watt", this.watts.getName());

        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
        int power = nbt.getInt("power");
        String watt = nbt.getString("watt");

        this.watts = EnumWattsTypes.getWattsFromName(watt);
        this.power = power;
    }
}
