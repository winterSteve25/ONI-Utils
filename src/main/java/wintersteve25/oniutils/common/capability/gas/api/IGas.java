package wintersteve25.oniutils.common.capability.gas.api;

import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public interface IGas {

    /**
     * This method is used to update the atmosphere pressure, it should be called everytime the gas data changes
     * Also returns a boolean indicates that if gas can be added right now.
     */
    void updatePressure();

    boolean addGas(EnumGasTypes gas, double amount);

    void setGas(EnumGasTypes gas, double amount);

    boolean removeGas(EnumGasTypes gas, double amount);

    HashMap<EnumGasTypes, Double> getGas();

    double getPressure();

    void setPressure(double pressure);

    void setRequiredType(EnumGasTypes type);

    EnumGasTypes getRequiredType();

    CompoundNBT write();

    void read(CompoundNBT nbt);
}
