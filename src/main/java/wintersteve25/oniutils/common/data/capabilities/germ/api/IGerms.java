package wintersteve25.oniutils.common.data.capabilities.germ.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGerms extends INBTSerializable<CompoundTag> {
    void addGerm(EnumGermType germType, int amount);

    void setGerm(EnumGermType germType, int amount);

    void removeGerm(int amount);

    EnumGermType getGermType();

    int getGermAmount();
}
