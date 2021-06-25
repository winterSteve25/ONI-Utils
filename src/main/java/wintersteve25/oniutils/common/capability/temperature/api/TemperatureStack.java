package wintersteve25.oniutils.common.capability.temperature.api;

import net.minecraft.nbt.CompoundNBT;

/**
 * Default implementation of ITemperature
 */
public class TemperatureStack implements ITemperature {
    private double temperature = 0;

    @Override
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public void addTemperature(double temperature) {
        this.temperature+=temperature;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public boolean isHot() {
        return temperature > 80;
    }

    @Override
    public boolean isCold() {
        return temperature < -40;
    }

    @Override
    public boolean isComfort() {
        return !isHot() && !isCold();
    }

    @Override
    public CompoundNBT write() {
        CompoundNBT temperature = new CompoundNBT();

        temperature.putDouble("temperature", this.temperature);

        return temperature;
    }

    @Override
    public void read(CompoundNBT nbt) {
        this.temperature = nbt.getDouble("temperature");
    }
}
