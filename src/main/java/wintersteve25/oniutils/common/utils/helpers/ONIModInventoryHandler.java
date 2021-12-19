package wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.oniutils.client.gui.ONIBaseGuiTabModification;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.contents.modules.modifications.ModificationContext;
import wintersteve25.oniutils.common.contents.modules.modifications.ONIModification;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.RenderErrorPacket;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ONIModInventoryHandler extends ItemStackHandler {
    private final ModificationContext context;

    public ONIModInventoryHandler(ModificationContext context) {
        super(context.getMaxModAmount());
        this.context = context;
    }

    @Override
    public void onContentsChanged(int slot) {
        context.getParent().markDirty();
        context.getParent().updateBlock();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (context.getValidMods() == null || context.getValidMods().length == 0) {
            return false;
        }

        Item item = stack.getItem();
        List<EnumModifications> validMods = Arrays.asList(context.getValidMods());

        if (item instanceof ONIModification) {
            ONIModification mod = (ONIModification) item;
            if (validMods.contains(mod.getModType())) {
                return true;
            } else {
                sendError();
                return false;
            }
        }

        return false;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (context.getValidMods() == null || context.getValidMods().length == 0) {
            return stack;
        }

        Item item = stack.getItem();
        List<EnumModifications> validMods = Arrays.asList(context.getValidMods());

        if (item instanceof ONIModification) {
            ONIModification mod = (ONIModification) item;
            if (validMods.contains(mod.getModType())) {
                return super.insertItem(slot, stack, simulate);
            }
        }

        sendError();
        return stack;
    }

    private void sendError() {
        if (context.getParent() == null) return;
        if (context.getParent().getWorld() == null) return;

        if (context.getParent().isRemote()) {
            ONIBaseGuiTabModification.addError();
        } else {
            if (context.getParent().getWorld().getPlayers().get(0) == null) return;
            ONINetworking.sendToClient(new RenderErrorPacket(), (ServerPlayerEntity) context.getParent().getWorld().getPlayers().get(0));
        }
    }
}
