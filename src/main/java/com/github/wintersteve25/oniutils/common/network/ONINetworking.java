package com.github.wintersteve25.oniutils.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ONINetworking {
    private static final SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    static {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ONIUtils.MODID, "oniutils"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .simpleChannel();
    }

    public static void registerMessages() {
        INSTANCE.messageBuilder(PacketUpdateServerBE.class, nextID())
                .encoder(PacketUpdateServerBE::encode)
                .decoder(PacketUpdateServerBE::new)
                .consumer(PacketUpdateServerBE::handle)
                .add();

        INSTANCE.messageBuilder(PacketUpdateClientBE.class, nextID())
                .encoder(PacketUpdateClientBE::encode)
                .decoder(PacketUpdateClientBE::new)
                .consumer(PacketUpdateClientBE::handle)
                .add();

        INSTANCE.messageBuilder(PacketModification.class, nextID())
                .encoder(PacketModification::encode)
                .decoder(PacketModification::new)
                .consumer(PacketModification::handle)
                .add();

        INSTANCE.messageBuilder(PacketRenderError.class, nextID())
                .encoder((var1, var2) -> {})
                .decoder((buffer) -> new PacketRenderError())
                .consumer(PacketRenderError::handle)
                .add();

        INSTANCE.messageBuilder(PacketTriggerPlayerMove.class, nextID())
                .encoder(PacketTriggerPlayerMove::encode)
                .decoder(PacketTriggerPlayerMove::new)
                .consumer(PacketTriggerPlayerMove::handle)
                .add();
        
        INSTANCE.messageBuilder(PacketUpdateClientWorldBuildRequest.class, nextID())
                .encoder(PacketUpdateClientWorldBuildRequest::encode)
                .decoder(PacketUpdateClientWorldBuildRequest::new)
                .consumer(PacketUpdateClientWorldBuildRequest::handle)
                .add();
        
        INSTANCE.messageBuilder(PacketOpenBuilderToolUI.class, nextID())
                .encoder((var1, var2) -> {})
                .decoder((buffer) -> new PacketOpenBuilderToolUI())
                .consumer(PacketOpenBuilderToolUI::handle)
                .add();
    }

    public static SimpleChannel getInstance() {
        return INSTANCE;
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
