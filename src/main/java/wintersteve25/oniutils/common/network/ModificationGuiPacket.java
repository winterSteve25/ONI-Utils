package wintersteve25.oniutils.common.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import wintersteve25.oniutils.client.utils.ClientUtils;
import wintersteve25.oniutils.common.items.base.modifications.ONIModificationGUI;

import java.util.function.Supplier;

public class ModificationGuiPacket {

    private final ItemStack modification;
    private final int maxBonus;

    public ModificationGuiPacket(int maxBonus, ItemStack modification) {
        this.maxBonus = maxBonus;
        this.modification = modification;
    }

    public ModificationGuiPacket(PacketBuffer buffer) {
        this.maxBonus = buffer.readInt();
        this.modification = buffer.readItemStack();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeItemStack(modification);
        buffer.writeInt(maxBonus);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientUtils.openGui(new ONIModificationGUI(modification, maxBonus)));
        ctx.get().setPacketHandled(true);
    }
}
