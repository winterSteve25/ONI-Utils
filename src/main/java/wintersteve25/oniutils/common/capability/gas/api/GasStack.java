package wintersteve25.oniutils.common.capability.gas.api;

public class GasStack implements IGas{

    private EnumGasTypes gasTypes = EnumGasTypes.OXYGEN;
    private int gasAmount = 0;

    @Override
    public void addGas(EnumGasTypes gasType, int amount) {
        if (this.gasTypes == gasType) {
            this.gasAmount = this.gasAmount+amount;
        }
    }

    @Override
    public void setGas(EnumGasTypes gasType, int amount) {
        this.gasTypes = gasType;
        this.gasAmount = amount;
    }

    @Override
    public void removeGas(int amount) {
        this.gasAmount = this.gasAmount-amount;
    }

    @Override
    public void removeGas(EnumGasTypes gasTypes, int amount) {
        if (this.gasTypes == gasTypes) {
            this.gasAmount = this.gasAmount-amount;
        }
    }

    @Override
    public EnumGasTypes getGasType() {
        return gasTypes;
    }

    @Override
    public int getGasAmount() {
        return gasAmount;
    }
}
