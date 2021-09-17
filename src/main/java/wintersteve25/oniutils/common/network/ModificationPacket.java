package wintersteve25.oniutils.common.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.common.items.base.modifications.ONIBaseModification;
import wintersteve25.oniutils.common.items.base.modifications.ONIModificationGUI;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.function.Supplier;

public class ModificationPacket {

    private final ItemStack mod;
    private final int bonusData;
    private final byte type;

    public ModificationPacket(ItemStack mod, int bonusData, byte type) {
        this.mod = mod;
        this.bonusData = bonusData;
        this.type = type;
    }

    public ModificationPacket(PacketBuffer buffer) {
        this.mod = buffer.readItemStack();
        this.bonusData = buffer.readInt();
        this.type = buffer.readByte();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeItemStack(mod);
        buffer.writeInt(bonusData);
        buffer.writeByte(type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            switch (type) {
                case ONIConstants.PacketType.MODIFICATION_GUI:
                    ONIModificationGUI.open(mod, bonusData);
                    break;
                case ONIConstants.PacketType.MODIFICATION_DATA:
                    ONIBaseModification.setBonusDataToItemStack(ctx.get().getSender(), bonusData);
                    break;
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
