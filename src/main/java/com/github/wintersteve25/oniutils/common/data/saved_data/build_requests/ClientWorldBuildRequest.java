package com.github.wintersteve25.oniutils.common.data.saved_data.build_requests;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Set;

public class ClientWorldBuildRequest implements IWorldBuildRequest {

    public static final ClientWorldBuildRequest INSTANCE = new ClientWorldBuildRequest();
    
    private final WorldBuildRequest internal;
    
    public ClientWorldBuildRequest() {
        internal = new WorldBuildRequest();
    }
    
    public void update(CompoundTag tag) {
        internal.deserializeNBT(tag);
    }
    
    @Override
    public BlockState getRequest(BlockPos pos) {
        return internal.requests.get(pos);
    }

    @Override
    public Set<Map.Entry<BlockPos, BlockState>> getAllRequests() {
        return internal.requests.entrySet();
    }
}
