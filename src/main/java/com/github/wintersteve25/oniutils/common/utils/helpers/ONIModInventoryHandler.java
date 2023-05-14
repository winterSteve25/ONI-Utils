package com.github.wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import com.github.wintersteve25.oniutils.client.gui.ONIBaseGuiTabModification;
import com.github.wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import com.github.wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationContext;
import com.github.wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import com.github.wintersteve25.oniutils.common.network.ONINetworking;
import com.github.wintersteve25.oniutils.common.network.PacketRenderError;

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
        context.getParent().setChanged();
        context.getParent().updateBlock();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (context.getValidMods() == null || context.getValidMods().length == 0) {
            return false;
        }

        Item item = stack.getItem();
        List<EnumModifications> validMods = Arrays.asList(context.getValidMods());

        if (item instanceof ONIModificationItem) {
            ONIModificationItem mod = (ONIModificationItem) item;
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

        if (item instanceof ONIModificationItem) {
            ONIModificationItem mod = (ONIModificationItem) item;
            if (validMods.contains(mod.getModType())) {
                return super.insertItem(slot, stack, simulate);
            }
        }

        sendError();
        return stack;
    }

    private void sendError() {
        if (context.getParent() == null) return;
        if (context.getParent().getLevel() == null) return;

        if (context.getParent().isClient()) {
            ONIBaseGuiTabModification.addError();
        } else {
            if (context.getParent().getLevel().players().get(0) == null) return;
            ONINetworking.sendToClient(new PacketRenderError(), (ServerPlayer) context.getParent().getLevel().players().get(0));
        }
    }
}
