package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.placeholder.ONIPlaceHolderTE;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIBaseItem;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipe;
import com.github.wintersteve25.oniutils.common.network.ONINetworking;
import com.github.wintersteve25.oniutils.common.network.PacketOpenBlueprintUI;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import com.github.wintersteve25.oniutils.common.registries.ONIRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ONIBlueprintItem extends ONIBaseItem {

    public ONIBlueprintItem() {
        super(ONIUtils.defaultProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Direction face = pContext.getClickedFace();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockPos targetPosition = clickedPos.relative(face);
        Player player = pContext.getPlayer();

        Level level = pContext.getLevel();
        if (!level.isInWorldBounds(targetPosition)) return InteractionResult.PASS;

        BlockItem placeHolder = ONIBlocks.Misc.PLACEHOLDER_BLOCK.asItem();
        InteractionResult result = placeHolder.place(new BlockPlaceContext(level, player, pContext.getHand(), ItemStack.EMPTY, new BlockHitResult(pContext.getClickLocation(), face, targetPosition, false)));

        if (!result.consumesAction()) return InteractionResult.PASS;
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity blockEntity = serverLevel.getBlockEntity(targetPosition);
            if (blockEntity instanceof ONIPlaceHolderTE placeHolderTE) {
                placeHolderTE.init(getRecipe(player));
                placeHolderTE.updateBlock();
            }
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
            ONINetworking.sendToClient(new PacketOpenBlueprintUI(), (ServerPlayer) pPlayer);
            return InteractionResultHolder.success(itemstack);
        }

        return InteractionResultHolder.pass(itemstack);
    }

    public static void setRecipe(ServerPlayer player, ResourceLocation recipeRl) {
        CompoundTag tag = player.getMainHandItem().getOrCreateTag();
        tag.putString("recipe", recipeRl.toString());
    }

    private static BlueprintRecipe getRecipe(Player player) {
        CompoundTag tag = player.getMainHandItem().getOrCreateTag();
        ResourceLocation recipeRL = new ResourceLocation(tag.getString("recipe"));
        return BlueprintRecipe.getRecipeWithId(player.getLevel(), recipeRL).orElseThrow();
    }
}
