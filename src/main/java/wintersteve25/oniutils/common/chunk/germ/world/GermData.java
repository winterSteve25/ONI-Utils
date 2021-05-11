package wintersteve25.oniutils.common.chunk.germ.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import wintersteve25.oniutils.common.chunk.germ.world.api.IGermData;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.init.ONIMiscs;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

public class GermData implements INBTSerializable<CompoundNBT>, IGermData {
    private String name;
    private int color;
    private int amount;
    private GermData germType;

    public GermData() {
    }

    public GermData(String name, int color, int amount) {
        this.name = name;
        this.color = color;
        this.amount = amount;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();

        tag.putString("germType", TextHelper.langToReg(this.germType.toString()));
        tag.putInt("germColor", this.color);
        tag.putInt("germAmount", this.amount);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        this.germType = getGermByName(tag.getString("germType"));
        this.color = tag.getInt("germColor");
        this.amount = tag.getInt("germAmount");
    }

    @Override
    public GermData createGerm(int amount) {
        return new GermData(this.name, this.color, amount);
    }

    @Override
    public GermData getGermData() {
        return this.germType;
    }

    @Override
    public void setGermType(GermData germType) {
        this.germType = germType;
    }

    @Override
    public int getGermColor() {
        return this.color;
    }

    @Override
    public void setGermColor(int color) {
        this.color = color;
    }

    @Override
    public String getGermName() {
        return this.name;
    }

    @Override
    public void setGermName(String name) {
        this.name = name;
    }

    @Override
    public int getGermAmount() {
        return this.amount;
    }

    @Override
    public void setGermAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public GermData getGermByName(String name) {
        for (GermData germs : GermChunk.germDataList) {
            if (germs.getGermName().equals(name)) {
                return germs.getGermData();
            }
        }
        return null;
    }

    public void initGerm(GermData data) {
        if(ONIConfig.ENABLE_GERM.get())
            ONIMiscs.germTypeList.add(data);
    }
}
