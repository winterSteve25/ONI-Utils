package wintersteve25.oniutils.common.contents.modules.modifications;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.init.ONIItems;
import wintersteve25.oniutils.common.contents.base.ONIBaseItem;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;
import wintersteve25.oniutils.common.network.ModificationPacket;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.utils.ONIConstants;

import java.util.List;
import java.util.function.Supplier;

public class ONIModification extends ONIBaseItem {

    private final int maxBonus;
    private final EnumModifications modType;
    private final TextFormatting color;
    private final ITextComponent[] tooltips;

    private ONIModification(Properties properties, String regName, TextFormatting color, int maxBonus, EnumModifications modType, ITextComponent... tooltips) {
        super(properties, regName);
        this.maxBonus = maxBonus;
        this.modType = modType;
        this.color = color;
        this.tooltips = tooltips;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote()) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            playerIn.swing(handIn, true);
            ONINetworking.sendToClient(new ModificationPacket(heldItem, maxBonus, ONIConstants.PacketType.MODIFICATION_GUI), (ServerPlayerEntity) playerIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public EnumModifications getModType() {
        return modType;
    }

    public static ONIModification create(String regName, TextFormatting color, int maxBonus, EnumModifications modType, ITextComponent... tooltips) {
        ONIModification mod = new ONIModification(new Properties().group(ONIUtils.creativeTab).maxStackSize(1).setNoRepair(), regName, color, maxBonus, modType, tooltips);
        ONIItems.itemRegistryList.add(mod);
        return mod;
    }

    @Override
    public Supplier<TextFormatting> getColorName() {
        return ()->color;
    }

    @Override
    public Supplier<List<ITextComponent>> getTooltips() {
        return ()-> Lists.newArrayList(tooltips);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return getBonusDataFromItemStack(stack) != 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public static void setBonusDataToItemStack(ServerPlayerEntity modification, int bonusData) {
        CompoundNBT nbt = modification.getHeldItemMainhand().getOrCreateTag();
        nbt.putInt("oniutils_bonus", bonusData);
    }

    public static int getBonusDataFromItemStack(ItemStack modification) {
        CompoundNBT tagCompound = modification.getOrCreateTag();
        return tagCompound.getInt("oniutils_bonus");
    }
}
