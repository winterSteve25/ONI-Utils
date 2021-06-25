package wintersteve25.oniutils.common.capability.temperature;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import wintersteve25.oniutils.common.capability.temperature.api.ITemperature;
import wintersteve25.oniutils.common.capability.temperature.api.TemperatureStack;

import javax.annotation.Nullable;

public class TemperatureCapability {

    @CapabilityInject(ITemperature.class)
    public static Capability<ITemperature> CAPABILITY_TEMPERATURE;

    public static void register() {
        CapabilityManager.INSTANCE.register(ITemperature.class, new Storage(), TemperatureStack::new);
    }


    public static class Storage implements Capability.IStorage<ITemperature> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<ITemperature> capability, ITemperature instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();

            nbt.putDouble("temperatureStorage", instance.getTemperature());

            return nbt;
        }

        @Override
        public void readNBT(Capability<ITemperature> capability, ITemperature instance, Direction side, INBT nbt) {
            instance.setTemperature(((CompoundNBT) nbt).getDouble("temperatureStorage"));
        }
    }
}
