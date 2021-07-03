package wintersteve25.oniutils.common.capability.durability.api;

import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;

public interface IDurability {
    int getDurability();

    void setDurability(int durability);

    CompoundNBT write();

    void read(CompoundNBT nbt);
}
