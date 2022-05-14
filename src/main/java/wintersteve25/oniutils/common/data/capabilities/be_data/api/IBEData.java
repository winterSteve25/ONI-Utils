package wintersteve25.oniutils.common.data.capabilities.be_data.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;

public interface IBEData {
    int getTemperature();

    void setTemperature(int in);

    int getDurability();

    void setDurability(int in);

    BlockPos getMyPos();

    void setMyPos(BlockPos pos);

    CompoundTag write();

    void read(CompoundTag nbt);
}
