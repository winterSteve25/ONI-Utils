package wintersteve25.oniutils.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import wintersteve25.oniutils.ONIUtils;

public class ONINetworking {
    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        ONIUtils.LOGGER.info("Registering ONIUtils networkings");

        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ONIUtils.MODID, "oniutils"),
                () -> "1.0",
                s -> true,
                s -> true);
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
