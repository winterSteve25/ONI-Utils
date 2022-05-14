package wintersteve25.oniutils.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.function.Supplier;

public class PacketUpdateClientTE {

    private final BlockPos pos;
    private final byte packetType;
    private final CompoundTag compoundNBT;

    public PacketUpdateClientTE(BlockEntity teIn, byte packetType) {
        this.pos = teIn.getBlockPos();
        this.packetType = packetType;
        this.compoundNBT = null;
    }

    public PacketUpdateClientTE(BlockEntity teIn, byte packetType, CompoundTag compoundNBT) {
        this.pos = teIn.getBlockPos();
        this.packetType = packetType;
        this.compoundNBT = compoundNBT;
    }

    public PacketUpdateClientTE(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.packetType = buffer.readByte();
        this.compoundNBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(packetType);
        buffer.writeNbt(compoundNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        if (pos == null) {
            ONIUtils.LOGGER.warn("Requested update but position is null");
            return;
        }

        BlockEntity te = Minecraft.getInstance().level.getBlockEntity(pos);

        if (te == null) {
            ONIUtils.LOGGER.warn("Requested update at " + pos + " but no te is found");
            return;
        }

        if (packetType == ONIConstants.PacketType.SYNC_DATA) {
            if (compoundNBT == null) return;
            te.handleUpdateTag(compoundNBT);
        }
    }
}
