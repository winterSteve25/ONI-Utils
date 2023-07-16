package com.github.wintersteve25.oniutils.common.network;

import com.github.wintersteve25.oniutils.common.data.saved_data.requests.ClientDupeRequests;
import com.github.wintersteve25.oniutils.common.data.saved_data.requests.DupleRequests;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateClientWorldBuildRequest {
    
    private final CompoundTag compoundNBT;

    public PacketUpdateClientWorldBuildRequest(DupleRequests request) {
        this.compoundNBT = request.serializeNBT();
    }

    public PacketUpdateClientWorldBuildRequest(FriendlyByteBuf buffer) {
        this.compoundNBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeNbt(compoundNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientDupeRequests.INSTANCE.update(compoundNBT));
        ctx.get().setPacketHandled(true);
    }
}
