package wintersteve25.oniutils.common.capability.plasma.api;

import net.minecraft.nbt.CompoundNBT;

public interface IPlasma {
    void addPower(int power);

    void removePower(int power);

    int getPower();

    void setPower(int power);

    boolean canGenerate(int power);

    boolean canExtract(int power);

    void setCapacity(int capacity);

    int getCapacity();

    boolean isConsumer();

    void setIsConsumer(boolean isConsumer);

    CompoundNBT write();

    void read(CompoundNBT nbt);
}
