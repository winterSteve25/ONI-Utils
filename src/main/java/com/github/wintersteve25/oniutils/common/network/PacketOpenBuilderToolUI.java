package com.github.wintersteve25.oniutils.common.network;

import com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint.ONIBlueprintGui;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenBuilderToolUI {
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ONIBlueprintGui::open);
        ctx.get().setPacketHandled(true);
    }
}
