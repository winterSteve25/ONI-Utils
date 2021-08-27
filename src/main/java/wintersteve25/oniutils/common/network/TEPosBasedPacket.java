package wintersteve25.oniutils.common.network;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.function.Supplier;

public class TEPosBasedPacket {

    private final BlockPos pos;
    private final byte packetType;
    private final int thresholdValue;

    public TEPosBasedPacket(TileEntity teIn, byte packetType) {
        this.pos = teIn.getPos();
        this.packetType = packetType;
        this.thresholdValue = 0;
    }

    public TEPosBasedPacket(TileEntity teIn, byte packetType, int thresholdValueIn) {
        this.pos = teIn.getPos();
        this.packetType = packetType;
        this.thresholdValue = thresholdValueIn;
    }

    public TEPosBasedPacket(PacketBuffer buffer) {
        this.pos = buffer.readBlockPos();
        this.packetType = buffer.readByte();
        this.thresholdValue = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(packetType);
        buffer.writeInt(thresholdValue);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (pos != null) {
            ServerPlayerEntity player = ctx.get().getSender();
            ONIBaseTE te = WorldUtils.getTileEntity(ONIBaseTE.class, player.getServerWorld(), pos);
            BlockState block = WorldUtils.getBlockState(player.getServerWorld(), pos).get();
            if (te != null) {
                switch (packetType) {
                    case ONIConstants.PacketType.REDSTONE_INPUT:
                        ctx.get().enqueueWork(te::toggleInverted);
                        break;
                    case ONIConstants.PacketType.REDSTONE_OUTPUT_LOW:
                        if (block.getBlock() instanceof ONIIHasRedstoneOutput) {
                            ONIIHasRedstoneOutput tile = (ONIIHasRedstoneOutput) block.getBlock();
                            tile.setLowThreshold(thresholdValue);
                        } else {
                            ONIUtils.LOGGER.error("Sent redstone output packet but tile does not support redstone output, Pos: {}", pos);
                        }
                        break;
                    case ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH:
                        if (block.getBlock() instanceof ONIIHasRedstoneOutput) {
                            ONIIHasRedstoneOutput tile = (ONIIHasRedstoneOutput) block.getBlock();
                            tile.setHighThreshold(thresholdValue);
                        } else {
                            ONIUtils.LOGGER.error("Sent redstone output packet but tile does not support redstone output, Pos: {}", pos);
                        }
                        break;
                }
            }
        }
        ctx.get().setPacketHandled(true);
    }
}
