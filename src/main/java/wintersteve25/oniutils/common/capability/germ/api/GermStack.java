package wintersteve25.oniutils.common.capability.germ.api;

public class GermStack implements IGerms {

    private EnumGermTypes germType = EnumGermTypes.NOTHING;
    private int amount = 0;

    @Override
    public void addGerm(EnumGermTypes germType, int amount) {
        if (this.germType == EnumGermTypes.NOTHING && this.amount <= 0 && germType != EnumGermTypes.NOTHING && amount > 0) {
            setGerm(germType, amount);
        } else if (this.germType == germType) {
            this.amount = this.amount+amount;
        }
    }

    @Override
    public void setGerm(EnumGermTypes germType, int amount) {
        this.germType = germType;
        this.amount = amount;
    }

    @Override
    public void removeGerm(int amount) {
        this.amount = this.amount-amount;
    }

    @Override
    public EnumGermTypes getGermType() {
        return this.germType;
    }

    @Override
    public int getGermAmount() {
        return this.amount;
    }
}
