package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
            CompoundNBT nbt = new CompoundNBT();

            nbt.putString("gasName", instance.getGasType().getName());
            nbt.putInt("gasAmounts", instance.getGasAmount());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IGas> capability, IGas instance, Direction side, INBT nbt) {
            String gasName = ((CompoundNBT) nbt).getString("germName");
            EnumGasTypes gasType = EnumGasTypes.getGasFromName(gasName);

            int germAmounts = ((CompoundNBT) nbt).getInt("germAmounts");

            instance.setGas(gasType, germAmounts);
        }
    }
}
