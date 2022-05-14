package wintersteve25.oniutils.common.data.capabilities.plasma.api;

import net.minecraft.nbt.CompoundTag;

public class Plasma implements IPlasma {

    private int power = 0;
    private int capacity = 1000;
    private EnumPlasmaTileType type = EnumPlasmaTileType.DYNAMIC;

    public Plasma() {
    }

    public Plasma(int capacity, EnumPlasmaTileType type) {
        this.capacity = capacity;
        this.type = type;
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
    public EnumPlasmaTileType getTileType() {
        return type;
    }

    @Override
    public void setTileType(EnumPlasmaTileType type) {
        this.type = type;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        nbt.putInt("power", power);
        nbt.putString("type", type.name());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.power = nbt.getInt("power");
        this.type = EnumPlasmaTileType.valueOf(nbt.getString("type"));
    }
}
