package wintersteve25.oniutils.common.capability.durability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.durability.api.DurabilityStack;
import wintersteve25.oniutils.common.capability.durability.api.IDurability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DurabilityCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private DurabilityStack durability = new DurabilityStack();
    private final LazyOptional<IDurability> lazyOptional = LazyOptional.of(() -> durability);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    public DurabilityCapabilityProvider() {

    }

    public DurabilityCapabilityProvider(int durability) {
        this.durability.setDurability(durability);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == DurabilityCapability.DURABILITY_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (DurabilityCapability.DURABILITY_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) DurabilityCapability.DURABILITY_CAPABILITY.writeNBT(durability, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (DurabilityCapability.DURABILITY_CAPABILITY != null) {
            DurabilityCapability.DURABILITY_CAPABILITY.readNBT(durability, null, nbt);
        }
    }
}
