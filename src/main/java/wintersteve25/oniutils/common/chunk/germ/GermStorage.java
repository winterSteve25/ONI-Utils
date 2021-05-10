package wintersteve25.oniutils.common.chunk.germ;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import wintersteve25.oniutils.common.chunk.germ.api.IGermChunk;

import javax.annotation.Nullable;

public class GermStorage implements Capability.IStorage<IGermChunk> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IGermChunk> capability, IGermChunk instance, Direction side) {
        return new CompoundNBT();
    }

    @Override
    public void readNBT(Capability<IGermChunk> capability, IGermChunk instance, Direction side, INBT nbt) {

    }
}
