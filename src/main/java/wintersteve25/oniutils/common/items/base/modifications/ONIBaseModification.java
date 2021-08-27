package wintersteve25.oniutils.common.items.base.modifications;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.items.base.ONIToolTipColorNameItem;
import wintersteve25.oniutils.common.network.ModificationGuiPacket;
import wintersteve25.oniutils.common.network.ONINetworking;

public class ONIBaseModification extends ONIToolTipColorNameItem {
    private final int maxBonus;
    private int currentBonus = 100;

    public ONIBaseModification(Properties properties, String regName, boolean doRegularDataGen, TextFormatting color, int maxBonus, ITextComponent... tooltips) {
        super(properties, regName, doRegularDataGen, color, tooltips);
        this.maxBonus = maxBonus;
    }

    public int getMaxBonus() {
        return maxBonus;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote()) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            ONINetworking.sendToClient(new ModificationGuiPacket(maxBonus, heldItem), (ServerPlayerEntity) playerIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
