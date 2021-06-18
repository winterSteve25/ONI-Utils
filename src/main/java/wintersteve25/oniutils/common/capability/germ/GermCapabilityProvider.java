package wintersteve25.oniutils.common.capability.germ;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.capability.germ.api.IGerms;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GermCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private final GermStack germ = new GermStack();
    private final LazyOptional<IGerms> lazyOptional = LazyOptional.of(() -> germ);

    public void invalidate() {
        lazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == GermsCapability.GERM_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (GermsCapability.GERM_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) GermsCapability.GERM_CAPABILITY.writeNBT(germ, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (GermsCapability.GERM_CAPABILITY != null) {
            GermsCapability.GERM_CAPABILITY.readNBT(germ, null, nbt);
        }
    }
}
