package com.github.wintersteve25.oniutils.common.network;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.data.saved_data.build_requests.ClientWorldBuildRequest;
import com.github.wintersteve25.oniutils.common.data.saved_data.build_requests.WorldBuildRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateClientWorldBuildRequest {
    
    private final CompoundTag compoundNBT;

    public PacketUpdateClientWorldBuildRequest(WorldBuildRequest request) {
        this.compoundNBT = request.serializeNBT();
    }

    public PacketUpdateClientWorldBuildRequest(FriendlyByteBuf buffer) {
        this.compoundNBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(compoundNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientWorldBuildRequest.INSTANCE.update(compoundNBT));
        ctx.get().setPacketHandled(true);
    }
}
