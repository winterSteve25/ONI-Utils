package wintersteve25.oniutils.common.capability.gas.api;

public interface IGas {
    void addGas(EnumGasTypes gasType, int amount);

    void setGas(EnumGasTypes gasType, int amount);

    void removeGas(int amount);

    void removeGas(EnumGasTypes gasTypes, int amount);

    EnumGasTypes getGasType();

    int getGasAmount();
}
