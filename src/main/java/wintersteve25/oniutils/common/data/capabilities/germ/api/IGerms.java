package wintersteve25.oniutils.common.data.capabilities.germ.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGerms extends INBTSerializable<CompoundTag> {
    void addGerm(EnumGermTypes germType, int amount);

    void setGerm(EnumGermTypes germType, int amount);

    void removeGerm(int amount);

    EnumGermTypes getGermType();

    int getGermAmount();
}
