package wintersteve25.oniutils.common.network;

import mekanism.common.util.WorldUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;

import java.util.function.Supplier;

public class TERedstoneButtonPacket {

    private BlockPos pos;

    public TERedstoneButtonPacket(TileEntity teIn) {
        this.pos = teIn.getPos();
    }

    public TERedstoneButtonPacket(PacketBuffer buffer) {
        this.pos = buffer.readBlockPos();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (pos != null) {
            ServerPlayerEntity player = ctx.get().getSender();
            ONIBaseTE te = WorldUtils.getTileEntity(ONIBaseTE.class, player.getServerWorld(), pos);

            if (te != null) {
                ctx.get().enqueueWork(te::toggleInverted);
            }
        }
        ctx.get().setPacketHandled(true);
    }
}
