package com.github.wintersteve25.oniutils.common.data.saved_data.requests;

import com.github.wintersteve25.oniutils.common.utils.SerializableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public class DupleRequests implements INBTSerializable<CompoundTag> {
    public SerializableMap<BlockPos, BlockState> buildRequests;
   
    public DupleRequests() {
        buildRequests = new SerializableMap<>(
                NbtUtils::writeBlockPos,
                NbtUtils::writeBlockState,
                tag -> NbtUtils.readBlockPos((CompoundTag) tag),
                tag -> NbtUtils.readBlockState((CompoundTag) tag),
                Tag.TAG_COMPOUND, Tag.TAG_COMPOUND
        );
    }
    
    @Override
    public CompoundTag serializeNBT() {
        return buildRequests.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        buildRequests.deserializeNBT(nbt);
    }
}
