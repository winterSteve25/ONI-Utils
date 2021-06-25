package wintersteve25.oniutils.common.capability.temperature;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.temperature.api.ITemperature;
import wintersteve25.oniutils.common.capability.temperature.api.TemperatureStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TemperatureCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private TemperatureStack temp = new TemperatureStack();
    private final LazyOptional<ITemperature> lazyOptional = LazyOptional.of(() -> temp);

    public TemperatureCapabilityProvider() {

    }

    public TemperatureCapabilityProvider(double temperature) {
        temp.setTemperature(temperature);
    }

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TemperatureCapability.CAPABILITY_TEMPERATURE) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (TemperatureCapability.CAPABILITY_TEMPERATURE == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) TemperatureCapability.CAPABILITY_TEMPERATURE.writeNBT(temp, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (TemperatureCapability.CAPABILITY_TEMPERATURE != null) {
            TemperatureCapability.CAPABILITY_TEMPERATURE.readNBT(temp, null, nbt);
        }
    }
}
