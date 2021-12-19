package wintersteve25.oniutils.common.capability.germ.api;

import net.minecraft.nbt.CompoundNBT;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

/**
 * Default implementation of IGerms
 */
public class GermStack implements IGerms {

    private EnumGermTypes germType = EnumGermTypes.NOTHING;
    private int amount = 0;

    @Override
    public void addGerm(EnumGermTypes germType, int amount) {
        if (this.germType == EnumGermTypes.NOTHING && this.amount <= 0 && germType != EnumGermTypes.NOTHING && amount > 0) {
            setGerm(germType, amount);
        } else if (this.germType == germType) {
            this.amount += amount;
        }
    }

    @Override
    public void setGerm(EnumGermTypes germType, int amount) {
        this.germType = germType;
        this.amount = amount;
    }

    @Override
    public void removeGerm(int amount) {
        this.amount = this.amount - amount;
    }

    @Override
    public EnumGermTypes getGermType() {
        return this.germType;
    }

    @Override
    public int getGermAmount() {
        return this.amount;
    }

    @Override
    public CompoundNBT write() {
        CompoundNBT germ = new CompoundNBT();

        germ.putString("germ", MiscHelper.langToReg(germType.getName()));
        germ.putInt("amount", this.amount);

        return germ;
    }

    @Override
    public void read(CompoundNBT nbt) {
        String germName = nbt.getString("germ");
        int germAmount = nbt.getInt("amount");

        germType = EnumGermTypes.getGermFromName(germName);
        amount = germAmount;
    }
}
