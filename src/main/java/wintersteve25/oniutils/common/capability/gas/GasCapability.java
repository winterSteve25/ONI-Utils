package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import wintersteve25.oniutils.common.capability.gas.api.EnumGasTypes;
import wintersteve25.oniutils.common.capability.gas.api.GasStack;
import wintersteve25.oniutils.common.capability.gas.api.IGas;

import javax.annotation.Nullable;

public class GasCapability {

    @CapabilityInject(IGas.class)
    public static Capability<IGas> GAS_CAPABILITY;

    public static void register() {
        CapabilityManager.INSTANCE.register(IGas.class, new GasStorage(), GasStack::new);
    }

    public static class GasStorage implements Capability.IStorage<IGas> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IGas> capability, IGas instance, Direction side) {
            ListNBT nbtTagList = new ListNBT();

            for (EnumGasTypes gas : instance.getGas().keySet()) {
                if (gas != null) {
                    CompoundNBT gasTag = new CompoundNBT();
                    gasTag.putString("gas", gas.getName());
                    gasTag.putDouble("amount", instance.getGas().get(gas));
                    nbtTagList.add(gasTag);
                }
            }

            CompoundNBT nbt = new CompoundNBT();
            nbt.put("gases", nbtTagList);
            nbt.putDouble("totalPressure", instance.getPressure());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IGas> capability, IGas instance, Direction side, INBT nbt) {
            instance.setPressure(((CompoundNBT) nbt).getDouble("totalPressure"));

            ListNBT tagList = ((CompoundNBT) nbt).getList("gases", Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.size(); i++) {
                CompoundNBT gasTag = tagList.getCompound(i);
                String gasName = gasTag.getString("gas");
                double amount = gasTag.getDouble("amount");
                instance.setGas(EnumGasTypes.getGasFromName(gasName), amount);
            }
        }
    }
}
