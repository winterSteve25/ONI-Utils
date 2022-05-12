package wintersteve25.oniutils.common.capability.circuits;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.circuits.api.WorldCircuits;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CircuitCapProv implements ICapabilitySerializable<CompoundNBT> {
    private final WorldCircuits circuits = new WorldCircuits();
    private final LazyOptional<WorldCircuits> lazyOptional = LazyOptional.of(() -> circuits);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CircuitCapability.WORLD_CIRCUIT_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CircuitCapability.WORLD_CIRCUIT_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CircuitCapability.WORLD_CIRCUIT_CAPABILITY.writeNBT(circuits, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CircuitCapability.WORLD_CIRCUIT_CAPABILITY != null) {
            CircuitCapability.WORLD_CIRCUIT_CAPABILITY.readNBT(circuits, null, nbt);
        }
    }
}
