package com.github.wintersteve25.oniutils.common.data.saved_data.build_requests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Set;

public interface IWorldBuildRequest {
    BlockState getRequest(BlockPos pos);
    
    Set<Map.Entry<BlockPos, BlockState>> getAllRequests();
}
