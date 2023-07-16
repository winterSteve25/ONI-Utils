package com.github.wintersteve25.oniutils.common.data.saved_data.build_requests;

import com.github.wintersteve25.oniutils.common.network.ONINetworking;
import com.github.wintersteve25.oniutils.common.network.PacketUpdateClientWorldBuildRequest;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.Map;
import java.util.Set;

public class ServerWorldBuildRequest extends SavedData implements IWorldBuildRequest {

    private final WorldBuildRequest internal;

    public ServerWorldBuildRequest() {
        internal = new WorldBuildRequest();
    }

    public ServerWorldBuildRequest(CompoundTag compoundTag) {
        internal = new WorldBuildRequest();
        
        if (!compoundTag.contains("requests")) {
            return;
        }
        
        internal.deserializeNBT(compoundTag.getCompound("requests"));
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        pCompoundTag.put("requests", internal.serializeNBT());
        return pCompoundTag;
    }

    @Override
    public BlockState getRequest(BlockPos pos) {
        return internal.requests.get(pos);
    }

    @Override
    public Set<Map.Entry<BlockPos, BlockState>> getAllRequests() {
        return internal.requests.entrySet();
    }

    public static ServerWorldBuildRequest get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(ServerWorldBuildRequest::new, ServerWorldBuildRequest::new, "oni_build_requests");
    }
    
    public static void updateClientData(ServerPlayer player) {
        ONINetworking.sendToClient(new PacketUpdateClientWorldBuildRequest(get(player.getLevel()).internal), player);
    }
}
