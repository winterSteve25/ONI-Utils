package wintersteve25.oniutils.common.capability.temperature.api;

import net.minecraft.nbt.CompoundNBT;

public interface ITemperature {

    /**
     * @param temperature
     * Takes a double parameter, sets the temperature
     */
    void setTemperature(double temperature);

    /**
     * @param temperature
     * Takes a double parameter, adds to the existing temperature
     */
    void addTemperature(double temperature);

    /**
     * @return
     * Returns current temperature
     */
    double getTemperature();

    /**
     * @return
     * Returns true if temperature is above 80
     */
    boolean isHot();

    /**
     * @return
     * Returns true if temperature is below -40
     */
    boolean isCold();

    /**
     * @return
     * Returns true if both isHot and isCold is false
     */
    boolean isComfort();

    /**
     * @return
     * Returns a CompoundNBT with nbt
     */
    CompoundNBT write();

    /**
     * @param nbt
     * Takes in a CompoundNBT and deserialize it
     */
    void read(CompoundNBT nbt);
}
