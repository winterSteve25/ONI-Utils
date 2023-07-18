package com.github.wintersteve25.oniutils.common.contents.base.blocks.placeholder;

import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseDirectional;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import mekanism.common.util.WorldUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ONIPlaceHolderBlock extends ONIBaseDirectional implements EntityBlock {
    public ONIPlaceHolderBlock() {
        this(Properties.of(Material.METAL).strength(-3.5F, 3600000.0F).requiresCorrectToolForDrops().dynamicShape().noOcclusion());
    }
    
    public ONIPlaceHolderBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ONIBlocks.Misc.PLACEHOLDER_TE.get().create(pPos, pState);
    }

    @Override
    @Deprecated
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ONIPlaceHolderTE te = getTileEntityOrThrow(ONIPlaceHolderTE.class, pLevel, pPos);
        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        return te.addItem(heldItem) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        ONIPlaceHolderTE te = getTileEntityOrThrow(ONIPlaceHolderTE.class, pLevel, pPos);
        pLevel.getServer().getPlayerList().broadcastMessage(new TranslatableComponent("oniutils.message.requests.buildCanceled", te.getInPlaceOf().getBlock().getName()), ChatType.GAME_INFO, Util.NIL_UUID);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ONIPlaceHolderTE te = getTileEntityOrThrow(ONIPlaceHolderTE.class, level, pos);
        
        if (te.getInPlaceOf() == null) {
            return ItemStack.EMPTY;
        }
        
        return new ItemStack(te.getInPlaceOf());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        ONIPlaceHolderTE te = WorldUtils.getTileEntity(ONIPlaceHolderTE.class, worldIn, pos);

        if (te == null) {
            return super.getShape(state, worldIn, pos, context);
        }
        
        return te.getInPlaceOf().getBlock().getShape(state, worldIn, pos, context);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {
    }
}
