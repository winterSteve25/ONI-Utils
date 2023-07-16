package com.github.wintersteve25.oniutils.common.data.saved_data.requests;

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

public class ServerDupeRequests extends SavedData implements IDupeRequests {

    private final DupleRequests internal;

    public ServerDupeRequests() {
        internal = new DupleRequests();
    }

    public ServerDupeRequests(CompoundTag compoundTag) {
        internal = new DupleRequests();
        
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

    public static ServerDupeRequests get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(ServerDupeRequests::new, ServerDupeRequests::new, "oni_build_requests");
    }
    
    public static void updateClientData(ServerPlayer player) {
        ONINetworking.sendToClient(new PacketUpdateClientWorldBuildRequest(get(player.getLevel()).internal), player);
    }
}
