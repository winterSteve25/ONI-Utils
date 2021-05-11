package wintersteve25.oniutils.common.chunk.germ.world;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import wintersteve25.oniutils.common.chunk.germ.world.api.IGermChunk;
import wintersteve25.oniutils.common.chunk.germ.world.api.IGermData;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class GermChunk implements IGermChunk {

    public static List<GermData> germDataList = Lists.newArrayList();

    @Override
    public void addGermData(IGermData germType) {
        if (germType instanceof GermData) {
            GermData data = ((GermData) germType);
            germDataList.add(data);
        } else {
            throw new UnsupportedOperationException("Unsupported Germtype, use GermData");
        }
    }

    @Override
    public void removeGermData(IGermData germType) {
        if (germType instanceof GermData) {
            GermData data = ((GermData) germType);
            germDataList.remove(data);
        } else {
            throw new UnsupportedOperationException("Unsupported Germtype, use GermData");
        }
    }

    @Override
    public int getSize() {
        return germDataList.size();
    }

    @Override
    public GermData getGerm(int index) {
        return germDataList.get(index);
    }

    @Override
    public boolean hasGerm() {
        if (!germDataList.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<IGermData> iterator() {
        return germDataList.stream().map(e -> (IGermData)e).iterator();
    }

    @Override
    public Spliterator<IGermData> spliterator() {
        return germDataList.stream().map(e -> (IGermData)e).spliterator();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        ListNBT listNBT = new ListNBT();
        germDataList.stream().map(GermData::serializeNBT).forEach(listNBT::add);
        tag.put("germDataChunk", listNBT);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT listNBT = nbt.getList("germDataChunk", Constants.NBT.TAG_COMPOUND);
        germDataList.clear();
        StreamSupport.stream(listNBT.spliterator(), false).map(e -> (CompoundNBT) e).forEach(e ->{
            GermData data = new GermData();
            data.deserializeNBT(e);
            germDataList.add(data);
        });
    }
}
