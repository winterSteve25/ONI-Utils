package wintersteve25.oniutils.common.data.capabilities.germ.api;

import net.minecraft.nbt.CompoundTag;

/**
 * Default implementation of IGerms
 */
public class Germs implements IGerms {

    private EnumGermType germType = EnumGermType.NOTHING;
    private int amount = 0;

    @Override
    public void addGerm(EnumGermType germType, int amount) {
        if (this.germType == EnumGermType.NOTHING && this.amount <= 0 && germType != EnumGermType.NOTHING && amount > 0) {
            setGerm(germType, amount);
        } else if (this.germType == germType) {
            this.amount += amount;
        }
    }

    @Override
    public void setGerm(EnumGermType germType, int amount) {
        this.germType = germType;
        this.amount = amount;
    }

    @Override
    public void removeGerm(int amount) {
        this.amount = this.amount - amount;
    }

    @Override
    public EnumGermType getGermType() {
        return this.germType;
    }

    @Override
    public int getGermAmount() {
        return this.amount;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag germ = new CompoundTag();

        germ.putString("germ", germType.getName());
        germ.putInt("amount", this.amount);

        return germ;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        String germName = nbt.getString("germ");
        int germAmount = nbt.getInt("amount");
        var type = EnumGermType.getGermFromName(germName);
        germType = type == null ? EnumGermType.NOTHING : type;
        amount = germAmount;
    }
}
