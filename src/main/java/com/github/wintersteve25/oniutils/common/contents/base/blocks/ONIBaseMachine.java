package com.github.wintersteve25.oniutils.common.contents.base.blocks;

import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIIBoundingBlock;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIHasGui;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIHasRedstoneOutput;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIModifiable;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIRequireSkillToInteract;
import com.github.wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.SkillType;
import com.github.wintersteve25.oniutils.common.registries.ONICapabilities;
import com.github.wintersteve25.oniutils.common.utils.helpers.ISHandlerHelper;
import com.github.wintersteve25.oniutils.common.utils.helpers.LangHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class ONIBaseMachine<BE extends BlockEntity> extends ONIBaseDirectional implements EntityBlock {

    // block builder properties
    private ONIIHasGui gui;
    private final Class<BE> beClass;
    private final TileEntityTypeRegistryObject<BE> blockEntityType;

    public ONIBaseMachine(Properties properties, Class<BE> beClass, TileEntityTypeRegistryObject<BE> blockEntityType) {
        super(properties);
        this.beClass = beClass;
        this.blockEntityType = blockEntityType;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        if (!world.isClientSide()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            ItemStack heldItem = player.getItemInHand(hand);
            super.use(state, world, pos, player, hand, rayTraceResult);
            if (isCorrectTe(tileEntity)) {
                if (tileEntity instanceof ONIIModifiable && tileEntity instanceof ONIBaseTE baseTE) {
                    baseTE.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
                    if (!heldItem.isEmpty() && heldItem.getItem() instanceof ONIModificationItem) {
                        ONIIModifiable modifiable = (ONIIModifiable) tileEntity;
                        if (modifiable.addMod((ONIBaseTE) tileEntity, heldItem)) {
                            player.swing(hand, true);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

                if (tileEntity instanceof ONIIRequireSkillToInteract skill) {
                    for (SkillType skillRequired : skill.requiredSkill().keySet()) {
                        var cap = player.getCapability(ONICapabilities.PLAYER);
                        var level = skill.getRequiredLevel(skillRequired);
                        if (cap.isPresent() && cap.resolve().map(pData -> pData.getSkills().get(skillRequired) < level).orElse(false)) {
                            player.displayClientMessage(new TranslatableComponent("oniutils.message.needLevel", level, LangHelper.skill(skillRequired)), true);
                            return InteractionResult.FAIL;
                        }
                    }
                }

                if (gui != null || this instanceof ONIIHasGui) {
                    if (gui == null) {
                        gui = (ONIIHasGui) this;
                    }
                    if (gui.machineName() != null) {
                        MenuProvider containerProvider = new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return gui.machineName();
                            }

                            @Override
                            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                                return gui.container(i, world, pos, playerInventory, playerEntity);
                            }
                        };
                        NetworkHooks.openGui((ServerPlayer) player, containerProvider, tileEntity.getBlockPos());
                    }
                }
            } else {
                ONIUtils.LOGGER.warn("Wrong tileEntity type found, failed to create container");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, @Nullable Direction side) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
            return ((ONIBaseTE) tile).canConnectRedstone(state, world, pos, side);
        }
        return super.canConnectRedstone(state, world, pos, side);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (isCorrectTe(world.getBlockEntity(pos))) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof ONIBaseTE baseTE) {
                baseTE.onHarvested(world, pos, state, player);
                if (baseTE instanceof ONIBaseInvTE) {
                    ONIBaseInvTE te = (ONIBaseInvTE) world.getBlockEntity(pos);
                    if (te != null) {
                        if (te.hasItem()) {
                            ISHandlerHelper.dropInventory(te, world, state, pos, te.getInvSize());
                        }

                        if (te instanceof ONIIModifiable modifiable) {
                            if (modifiable.modContext().containsUpgrades()) {
                                ISHandlerHelper.dropInventory(modifiable.modContext().getModHandler(), world, state, pos, modifiable.modContext().getMaxModAmount());
                            }
                        }
                    }
                }
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (!state.is(newState.getBlock()) || !newState.hasBlockEntity())) {
            BlockEntity tile = WorldUtils.getTileEntity(world, pos);
            if (tile instanceof ONIIBoundingBlock) {
                ((ONIIBoundingBlock) tile).onBreak(state);
            }
            if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
                ((ONIBaseTE) tile).onBroken(state, world, pos, newState, isMoving);
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = WorldUtils.getTileEntity(blockAccess, pos);
        if (tile instanceof ONIIHasRedstoneOutput redstoneOutput) {
            AtomicBoolean isLowTrue = new AtomicBoolean(false);
            AtomicBoolean isHighTrue = new AtomicBoolean(false);

            tile.getCapability(ONICapabilities.PLASMA).ifPresent(power -> {
                if ((power.getPower() / power.getCapacity()) * 100 < redstoneOutput.lowThreshold()) {
                    isLowTrue.set(true);
                }

                if ((power.getPower() / power.getCapacity()) * 100 > redstoneOutput.highThreshold()) {
                    isHighTrue.set(true);
                }
            });

            return isHighTrue.get() || isLowTrue.get() ? 15 : 0;
        }
        if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
            ((ONIBaseTE) tile).getWeakPower(blockState, blockAccess, pos, side);
        }

        return 0;
    }

    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntityType.get().create(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return ONIBaseTE::clientTicker;
        }
        return ONIBaseTE::serverTicker;
    }

    public boolean isCorrectTe(BlockEntity tile) {
        return getBeClass() != null && getBeClass().isInstance(tile);
    }

    public Class<? extends BlockEntity> getBeClass() {
        return beClass;
    }

    @Nullable
    public ONIIHasGui getGui() {
        return gui == null ? this instanceof ONIIHasGui ? (ONIIHasGui) this : null : gui;
    }

    public void setGui(ONIIHasGui gui) {
        this.gui = gui;
    }
}