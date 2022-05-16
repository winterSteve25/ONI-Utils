package wintersteve25.oniutils.common.network;

import mekanism.common.util.WorldUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.api.ONIIForceStoppable;
import wintersteve25.oniutils.api.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.function.Supplier;

public class PacketUpdateBE {

    private final BlockPos pos;
    private final byte packetType;
    private final int thresholdValue;

    public PacketUpdateBE(BlockEntity teIn, byte packetType) {
        this.pos = teIn.getBlockPos();
        this.packetType = packetType;
        this.thresholdValue = 0;
    }

    public PacketUpdateBE(BlockEntity teIn, byte packetType, int thresholdValueIn) {
        this.pos = teIn.getBlockPos();
        this.packetType = packetType;
        this.thresholdValue = thresholdValueIn;
    }

    public PacketUpdateBE(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.packetType = buffer.readByte();
        this.thresholdValue = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(packetType);
        buffer.writeInt(thresholdValue);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (pos != null) {
                ServerPlayer player = ctx.get().getSender();
                ONIBaseTE te = WorldUtils.getTileEntity(ONIBaseTE.class, player.getCommandSenderWorld(), pos);
                if (te != null) {
                    switch (packetType) {
                        case ONIConstants.PacketType.REDSTONE_INPUT:
                            if (te instanceof ONIIForceStoppable forceStoppable) {
                                forceStoppable.toggleInverted();
                            }
                            break;
                        case ONIConstants.PacketType.REDSTONE_OUTPUT_LOW:
                            if (te instanceof ONIIHasRedstoneOutput tile) {
                                tile.setLowThreshold(thresholdValue);
                            } else {
                                ONIUtils.LOGGER.warn("Sent redstone output packet but tile does not support redstone output, Pos: {}", pos);
                            }
                            break;
                        case ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH:
                            if (te instanceof ONIIHasRedstoneOutput tile) {
                                tile.setHighThreshold(thresholdValue);
                            } else {
                                ONIUtils.LOGGER.warn("Sent redstone output packet but tile does not support redstone output, Pos: {}", pos);
                            }
                            break;
                    }
                }
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
