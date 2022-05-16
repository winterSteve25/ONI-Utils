package wintersteve25.oniutils.common.contents.modules.items.modifications;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.registries.ONIItems;
import wintersteve25.oniutils.common.contents.base.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.network.PacketModification;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.List;
import java.util.function.Supplier;

public class ONIModificationItem extends ONIBaseItem {

    private final int maxBonus;
    private final EnumModifications modType;
    private final ChatFormatting color;
    private final Component[] tooltips;

    public ONIModificationItem(Properties properties, ChatFormatting color, int maxBonus, EnumModifications modType, Component... tooltips) {
        super(properties);
        this.maxBonus = maxBonus;
        this.modType = modType;
        this.color = color;
        this.tooltips = tooltips;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            playerIn.swing(handIn, true);
            ONINetworking.sendToClient(new PacketModification(heldItem, maxBonus, ONIConstants.PacketType.MODIFICATION_GUI), (ServerPlayer) playerIn);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    public EnumModifications getModType() {
        return modType;
    }

    @Override
    public Supplier<ChatFormatting> getColorName() {
        return ()->color;
    }

    @Override
    public Supplier<List<Component>> getTooltips() {
        return ()-> Lists.newArrayList(tooltips);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return getBonusDataFromItemStack(stack) != 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public static void setBonusDataToItemStack(ServerPlayer modification, int bonusData) {
        CompoundTag nbt = modification.getMainHandItem().getOrCreateTag();
        nbt.putInt("oniutils_bonus", bonusData);
    }

    public static int getBonusDataFromItemStack(ItemStack modification) {
        CompoundTag tagCompound = modification.getOrCreateTag();
        return tagCompound.getInt("oniutils_bonus");
    }
}