package wintersteve25.oniutils.common.capability.world_gas;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.world_gas.api.IWorldGas;
import wintersteve25.oniutils.common.capability.world_gas.api.WorldGasImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldGasCapProv implements ICapabilitySerializable<CompoundNBT>  {
    private final WorldGasImpl gas = new WorldGasImpl();
    private final LazyOptional<IWorldGas> lazyOptional = LazyOptional.of(() -> gas);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == WorldGasCapability.WORLD_GAS_CAP) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (WorldGasCapability.WORLD_GAS_CAP == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) WorldGasCapability.WORLD_GAS_CAP.writeNBT(gas, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (WorldGasCapability.WORLD_GAS_CAP != null) {
            WorldGasCapability.WORLD_GAS_CAP.readNBT(gas, null, nbt);
        }
    }
}
