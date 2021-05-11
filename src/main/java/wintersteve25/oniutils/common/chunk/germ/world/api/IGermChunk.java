package wintersteve25.oniutils.common.chunk.germ.world.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import wintersteve25.oniutils.common.chunk.germ.world.GermData;

import java.util.Spliterator;

public interface IGermChunk extends Iterable<IGermData>, INBTSerializable<CompoundNBT> {

    void addGermData(IGermData germType);

    void removeGermData(IGermData germType);

    int getSize();

    GermData getGerm(int index);

    boolean hasGerm();

    Spliterator<IGermData> spliterator();
}
