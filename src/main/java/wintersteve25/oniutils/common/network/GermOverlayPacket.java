package wintersteve25.oniutils.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.client.renderers.overlays.GermOverlay;
import wintersteve25.oniutils.common.utils.GermPacketHelper;

import java.util.function.Supplier;

public class GermOverlayPacket {

    private int germ;

    public GermOverlayPacket(int germ) {
        this.germ = germ;
    }

    public GermOverlayPacket(PacketBuffer buffer) {
        this.germ = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(germ);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (germ == GermPacketHelper.SlimeLung) {
            ctx.get().enqueueWork(GermOverlay::slimeLung);
        }
        ctx.get().setPacketHandled(true);
    }
}
