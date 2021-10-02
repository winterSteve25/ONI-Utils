package wintersteve25.oniutils.common.capability.oni_te_data.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public interface ONIITEData {
    int getTemperature();

    void setTemperature(int in);

    int getDurability();

    void setDurability(int in);

    BlockPos getMyPos();

    void setMyPos(BlockPos pos);

    CompoundNBT write();

    void read(CompoundNBT nbt);
}
