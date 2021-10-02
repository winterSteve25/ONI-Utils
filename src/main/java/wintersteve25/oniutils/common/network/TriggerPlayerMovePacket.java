package wintersteve25.oniutils.common.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.common.registration.PlayerMovingEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class TriggerPlayerMovePacket {
    private final UUID player;
    private final PlayerMovingEvent.MovementTypes movementTypes;

    public TriggerPlayerMovePacket(UUID player, PlayerMovingEvent.MovementTypes movementTypes) {
        this.player = player;
        this.movementTypes = movementTypes;
    }

    public TriggerPlayerMovePacket(PacketBuffer buffer) {
        this.player = buffer.readUniqueId();
        this.movementTypes = Enum.valueOf(PlayerMovingEvent.MovementTypes.class, buffer.readString());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeUniqueId(player);
        buffer.writeString(movementTypes.toString());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity playerEntity = ctx.get().getSender().getEntityWorld().getPlayerByUuid(player);
            MinecraftForge.EVENT_BUS.post(new PlayerMovingEvent(movementTypes, playerEntity));
        });
        ctx.get().setPacketHandled(true);
    }
}
