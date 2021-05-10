package wintersteve25.oniutils.common.chunk.germ;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.chunk.Chunk;
import wintersteve25.oniutils.common.chunk.germ.api.IGermChunk;
import wintersteve25.oniutils.common.chunk.germ.api.IGermData;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

import java.util.Iterator;

public class GermChunk implements IGermChunk {

    private Chunk chunk;
    private int germInChunk = 0;
    private GermData germTypeInChunk = null;

    public GermChunk(Chunk chunk, int germAmount, GermData germType) {
        this.chunk = chunk;
        this.germInChunk = germAmount;
        this.germTypeInChunk = germType;
    }

    @Override
    public void addGerm(IGermData germType, int amount) {
        if (germType instanceof GermData) {

            if (germType != null) {
                if (germTypeInChunk == null || germTypeInChunk == germType) {
                    if (amount > 0) {
                        this.germInChunk += amount;
                        this.germTypeInChunk = germType;
                        markDirty();
                    }
                }
            }
        }
    }

    @Override
    public void removeGermAmount(IGermData germType, int amount) {
        if (germTypeInChunk != null) {
            if (germInChunk > 0) {
                if (germTypeInChunk == germType) {
                    germInChunk -= amount;
                    markDirty();
                }
            }
        }
    }

    @Override
    public void removeAllGerm(IGermData germType) {
        if (germTypeInChunk != null) {
            if (germInChunk > 0) {
                if (germTypeInChunk == germType) {
                    germTypeInChunk = null;
                    germInChunk = 0;
                    markDirty();
                }
            }
        }
    }

    @Override
    public void removeAllGerm() {
        germTypeInChunk = null;
        germInChunk = 0;
        markDirty();
    }

    @Override
    public void removeGermFromChunk(Chunk chunk) {
        if (chunk != null) {
        }
    }

    @Override
    public GermData getGerm(Chunk chunk) {
        return null;
    }

    @Override
    public boolean hasGerm(Chunk chunk) {
        return false;
    }

    @Override
    public void markDirty() {
        this.chunk.markUnsaved();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();

        tag.putString("germType", TextHelper.langToReg(germTypeInChunk.toString()));
        tag.putInt("germAmount", germInChunk);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        tag.getString("germType");
        tag.getInt("germAmount");
    }

    @Override
    public Iterator<IGermData> iterator() {
        return null;
    }
}
