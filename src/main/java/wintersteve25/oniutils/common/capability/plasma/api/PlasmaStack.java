package wintersteve25.oniutils.common.capability.plasma.api;

import net.minecraft.nbt.CompoundNBT;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;

public class PlasmaStack implements IPlasma {

    private int power = 0;
    private int capacity = 1000;
    private boolean consumer = false;

    public PlasmaStack() {
    }

    public PlasmaStack(int capacity, boolean isConsumer) {
        this.capacity = capacity;
        this.consumer = isConsumer;
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
    public boolean isConsumer() {
        return consumer;
    }

    @Override
    public void setIsConsumer(boolean isConsumer) {
        this.consumer = isConsumer;
    }

    @Override
    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("power", power);
        nbt.putBoolean("isConsumer", this.consumer);

        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
        int power = nbt.getInt("power");

        this.power = power;
        this.consumer = nbt.getBoolean("isConsumer");
    }
}
