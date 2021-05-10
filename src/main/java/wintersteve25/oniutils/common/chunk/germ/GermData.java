package wintersteve25.oniutils.common.chunk.germ;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import wintersteve25.oniutils.common.chunk.germ.api.IGermData;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

import java.util.ArrayList;
import java.util.List;

public class GermData implements INBTSerializable<CompoundNBT>, IGermData {
    private String name;
    private int color;
    private int amount;
    private GermData germType;

    public static final GermData SLIMELUNG = new GermData("Slime Lung", 0x008020, 0);
    public static final GermData FLORALSCENTS= new GermData("Floral Scents", 0xd585e6, 0);
    public static final GermData FOODPOISON= new GermData("Food Poisoning", 0x17e64b, 0);
    public static final GermData ZOMBIESPORES= new GermData("Zombie Spores", 0x3ab4e8, 0);

    public List<GermData> germList = new ArrayList<>();

    public GermData(String name, int color, int amount) {
        this.name = name;
        this.color = color;
        this.amount = amount;
    }

    public void init() {
        germList.add(SLIMELUNG);
        germList.add(FLORALSCENTS);
        germList.add(FOODPOISON);
        germList.add(ZOMBIESPORES);
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
    public GermData getGermType() {
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
        for (GermData germs : germList) {
            if (germs.getGermName().equals(name)) {
                return germs.getGermType();
            }
        }
        return null;
    }
}
