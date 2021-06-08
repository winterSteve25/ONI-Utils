package wintersteve25.oniutils.common.chunk.germ;

import wintersteve25.oniutils.common.chunk.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.chunk.germ.api.IGerms;

public class GermStack implements IGerms {

    private EnumGermTypes germType;
    private int amount;

    @Override
    public void addGerm(EnumGermTypes germType, int amount) {
        if (this.germType == null && this.amount <= 0) {
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
    public EnumGermTypes getGermType() {
        return this.germType;
    }

    @Override
    public int getGermAmount() {
        return this.amount;
    }
}
