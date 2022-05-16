package wintersteve25.oniutils.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import wintersteve25.oniutils.ONIUtils;

import java.util.function.Supplier;

public class PacketUpdateClientBE {

    private final BlockPos pos;
    private final CompoundTag compoundNBT;

    public PacketUpdateClientBE(BlockEntity teIn) {
        this.pos = teIn.getBlockPos();
        this.compoundNBT = null;
    }

    public PacketUpdateClientBE(BlockEntity teIn, CompoundTag compoundNBT) {
        this.pos = teIn.getBlockPos();
        this.compoundNBT = compoundNBT;
    }

    public PacketUpdateClientBE(FriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.compoundNBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeNbt(compoundNBT);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (pos == null) {
                ONIUtils.LOGGER.error("Requested update but position is null");
                return;
            }

            ClientLevel level = Minecraft.getInstance().level;

            if (level == null) {
                ONIUtils.LOGGER.error("Requested update at {} but level does not exist", pos);
                return;
            }

            BlockEntity te = level.getBlockEntity(pos);

            if (te == null) {
                ONIUtils.LOGGER.error("Requested update at {} but no te is found", pos);
                return;
            }
            if (compoundNBT == null) return;

            te.handleUpdateTag(compoundNBT);
        });
        ctx.get().setPacketHandled(true);
    }
}
