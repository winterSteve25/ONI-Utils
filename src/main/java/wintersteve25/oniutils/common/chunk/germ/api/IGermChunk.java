package wintersteve25.oniutils.common.chunk.germ.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.INBTSerializable;
import wintersteve25.oniutils.common.chunk.germ.GermData;

public interface IGermChunk extends Iterable<IGermData>, INBTSerializable<CompoundNBT> {
    void addGerm(IGermData germType, int amount);

    void removeGermAmount(IGermData germType, int amount);

    void removeAllGerm(IGermData germType);

    void removeAllGerm();

    void removeGermFromChunk(Chunk chunk);

    GermData getGerm(Chunk chunk);

    boolean hasGerm(Chunk chunk);

    void markDirty();
}
