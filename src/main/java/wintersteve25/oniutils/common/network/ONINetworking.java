package wintersteve25.oniutils.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import wintersteve25.oniutils.ONIUtils;

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
        ONIUtils.LOGGER.info("Registering ONIUtils networkings");

        INSTANCE.messageBuilder(TEPosBasedPacket.class, nextID())
                .encoder(TEPosBasedPacket::encode)
                .decoder(TEPosBasedPacket::new)
                .consumer(TEPosBasedPacket::handle)
                .add();

        INSTANCE.messageBuilder(ModificationPacket.class, nextID())
                .encoder(ModificationPacket::encode)
                .decoder(ModificationPacket::new)
                .consumer(ModificationPacket::handle)
                .add();

        INSTANCE.messageBuilder(RenderErrorPacket.class, nextID())
                .encoder((var1, var2) -> {})
                .decoder((buffer) -> new RenderErrorPacket())
                .consumer(RenderErrorPacket::handle)
                .add();

        INSTANCE.messageBuilder(TriggerPlayerMovePacket.class, nextID())
                .encoder(TriggerPlayerMovePacket::encode)
                .decoder(TriggerPlayerMovePacket::new)
                .consumer(TriggerPlayerMovePacket::handle)
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
