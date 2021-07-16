package wintersteve25.oniutils.common.capability.durability.api;

import net.minecraft.nbt.CompoundNBT;

public class DurabilityStack implements IDurability {

    private int durability = 0;

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public CompoundNBT write() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("ONIDurability", durability);
        return nbt;
    }

    @Override
    public void read(CompoundNBT nbt) {
       this.durability = nbt.getInt("ONIDurability");
    }
}
