package wintersteve25.oniutils.common.capability.plasma.api;

import net.minecraft.nbt.CompoundNBT;

public interface IPlasma {
    void addPower(int power);

    int getPower();

    void setPower(int power);

    void setCapacity(int capacity);

    int getCapacity();

    void setWatts(EnumWattsTypes watts);

    EnumWattsTypes getWatts();

    CompoundNBT write();

    void read(CompoundNBT nbt);
}
