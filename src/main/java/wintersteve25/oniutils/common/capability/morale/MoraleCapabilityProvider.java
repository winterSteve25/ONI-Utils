package wintersteve25.oniutils.common.capability.morale;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.morale.api.IMorale;
import wintersteve25.oniutils.common.capability.morale.api.MoraleStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MoraleCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private MoraleStack morale = new MoraleStack();
    private final LazyOptional<IMorale> lazyOptional = LazyOptional.of(() -> morale);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == MoraleCapability.MORALE_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (MoraleCapability.MORALE_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) MoraleCapability.MORALE_CAPABILITY.writeNBT(morale, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (MoraleCapability.MORALE_CAPABILITY != null) {
            MoraleCapability.MORALE_CAPABILITY.readNBT(morale, null, nbt);
        }
    }
}
