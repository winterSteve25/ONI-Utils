package wintersteve25.oniutils.common.chunk.germ;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import wintersteve25.oniutils.common.chunk.germ.api.IGermChunk;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GermCapProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IGermChunk.class)
    public static final Capability<IGermChunk> GERM_CHUNK_CAP = null;

    private LazyOptional<IGermChunk> germInstance = LazyOptional.of(GERM_CHUNK_CAP::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == GERM_CHUNK_CAP) {
            return germInstance.cast();
        }
        return getCapability(cap, side);
    }

    @Override
    public INBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(INBT nbt) {

    }
}
