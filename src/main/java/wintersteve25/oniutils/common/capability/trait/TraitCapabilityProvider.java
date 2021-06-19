package wintersteve25.oniutils.common.capability.trait;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.trait.api.DefaultTrait;
import wintersteve25.oniutils.common.capability.trait.api.ITrait;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TraitCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultTrait trait = new DefaultTrait();
    private final LazyOptional<ITrait> lazyOptional = LazyOptional.of(() -> trait);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TraitCapability.TRAIT_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (TraitCapability.TRAIT_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) TraitCapability.TRAIT_CAPABILITY.writeNBT(trait, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (TraitCapability.TRAIT_CAPABILITY != null) {
            TraitCapability.TRAIT_CAPABILITY.readNBT(trait, null, nbt);
        }
    }
}
