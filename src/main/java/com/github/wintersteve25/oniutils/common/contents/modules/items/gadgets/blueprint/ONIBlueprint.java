package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import com.github.wintersteve25.oniutils.common.network.ONINetworking;
import com.github.wintersteve25.oniutils.common.network.PacketOpenBuilderToolUI;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ONIBlueprint extends ONIBaseItem {
    public ONIBlueprint() {
        super(ONIUtils.defaultProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var targetPosition = pContext.getClickedPos().relative(pContext.getClickedFace());
        
        // this is called on both sides, so we just update both sides manually instead of sending an update packet
        if (pContext.getLevel() instanceof ServerLevel serverLevel) {
            
        } else {
            
        }
        
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        
        if (pLevel.isClientSide()) {
            return InteractionResultHolder.pass(itemstack);
        }
        
        if (pPlayer.isCrouching() && pPlayer.pick(5, 0, false).getType() == HitResult.Type.MISS) {
            ONINetworking.sendToClient(new PacketOpenBuilderToolUI(), (ServerPlayer) pPlayer);
            return InteractionResultHolder.success(itemstack);
        }
        
        return InteractionResultHolder.pass(itemstack);
    }
}
