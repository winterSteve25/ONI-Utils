package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.gas.api.EnumGasTypes;
import wintersteve25.oniutils.common.capability.gas.api.GasStack;
import wintersteve25.oniutils.common.capability.gas.api.IGas;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private GasStack gas = new GasStack();
    private final LazyOptional<IGas> lazyOptional = LazyOptional.of(() -> gas);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    public GasCapabilityProvider() {
        gas.addGas(EnumGasTypes.OXYGEN, 10000);
    }

    public GasCapabilityProvider(GasStack stack) {
        this.gas = stack;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == GasCapability.GAS_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (GasCapability.GAS_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) GasCapability.GAS_CAPABILITY.writeNBT(gas, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (GasCapability.GAS_CAPABILITY != null) {
            GasCapability.GAS_CAPABILITY.readNBT(gas, null, nbt);
        }
    }
}
