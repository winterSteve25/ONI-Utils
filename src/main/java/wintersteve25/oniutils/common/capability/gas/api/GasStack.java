package wintersteve25.oniutils.common.capability.gas.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

import java.util.HashMap;

public class GasStack implements IGas {

    private HashMap<EnumGasTypes, Double> gases = new HashMap<>();
    private HashMap<EnumGasTypes, Double> pressure = new HashMap<>();
    private EnumGasTypes requiredType = EnumGasTypes.OXYGEN;
    private double totalPressure = 0;

    @Override
    public void updatePressure() {
        totalPressure = 0;

        for (EnumGasTypes gas : gases.keySet()) {
            double amount = gases.get(gas);

            if (!pressure.containsKey(gas)) {
                pressure.putIfAbsent(gas, amount/50);
            } else {
                pressure.replace(gas, amount/50);
            }

        }

        for (EnumGasTypes gas : pressure.keySet()) {
            totalPressure += pressure.get(gas);
        }
    }

    @Override
    public boolean addGas(EnumGasTypes gas, double amount) {
        updatePressure();
        if (getPressure() < 100) {
            if(gases.containsKey(gas)) {
                double currentAmount = gases.get(gas);
                gases.replace(gas, currentAmount + amount);
            }
            else {
                gases.putIfAbsent(gas, amount);
            }
            updatePressure();
            return true;
        }
        return false;
    }

    @Override
    public void setGas(EnumGasTypes gas, double amount) {
        if (gases.containsKey(gas)) {
            gases.replace(gas, amount);
        } else {
            gases.putIfAbsent(gas, amount);
        }
    }

    @Override
    public void setGas(HashMap<EnumGasTypes, Double> gasMap) {
        this.gases = gasMap;
    }

    @Override
    public boolean removeGas(EnumGasTypes gas, double amount) {
        if (gases.containsKey(gas)) {
            if (gases.get(gas)-amount == 0) {
                gases.remove(gas);
            } else {
                gases.replace(gas, gases.get(gas)-amount);
            }
            return true;
        }
        return false;
    }

    @Override
    public double getGasAmountTotal() {
        double totalGasAmount = 0;

        for (EnumGasTypes gas : gases.keySet()) {
            totalGasAmount += gases.get(gas);
        }

        return  totalGasAmount;
    }

    @Override
    public HashMap<EnumGasTypes, Double> getGas() {
        return gases;
    }

    @Override
    public HashMap<EnumGasTypes, Double> getPressureMap() {
        return pressure;
    }

    @Override
    public double getPressure() {
        return totalPressure;
    }

    @Override
    public void setPressure(double pressure) {
        this.totalPressure = pressure;
    }

    @Override
    public void setRequiredType(EnumGasTypes type) {
        this.requiredType = type;
    }

    @Override
    public EnumGasTypes getRequiredType() {
        return this.requiredType;
    }

    @Override
    public CompoundNBT write() {
        ListNBT nbtTagList = new ListNBT();

        for (EnumGasTypes gas : gases.keySet()) {
            if (gas != null) {
                CompoundNBT gasTag = new CompoundNBT();
                gasTag.putString("Gas", MiscHelper.langToReg(gas.getName()));
                gasTag.putDouble("Amount", gases.get(gas));
                nbtTagList.add(gasTag);
            }
        }

        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Gases", nbtTagList);
        nbt.putDouble("TotalPressure", totalPressure);
        nbt.putString("RequiredGas", MiscHelper.langToReg(requiredType.getName()));

        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
       this.totalPressure = nbt.getDouble("TotalPressure");
       this.requiredType = EnumGasTypes.getGasFromName(nbt.getString("RequiredGas"));

       if (nbt.contains("Gases", Constants.NBT.TAG_LIST)) {
           ListNBT tagList = nbt.getList("Gases", Constants.NBT.TAG_COMPOUND);
           for (int i = 0; i < tagList.size(); i++) {
               CompoundNBT gasTag = tagList.getCompound(i);
               String gasName = gasTag.getString("Gas");
               double amount = gasTag.getDouble("Amount");
               setGas(EnumGasTypes.getGasFromName(gasName), amount);
           }
       }

    }
}
