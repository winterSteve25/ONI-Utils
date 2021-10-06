package wintersteve25.oniutils.common.blocks.base;

import harmonised.pmmo.api.APIUtils;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.network.NetworkHooks;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.interfaces.*;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.items.modules.modifications.ONIBaseModification;
import wintersteve25.oniutils.common.utils.ISHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ONIBaseMachine extends ONIBaseDirectional {
    private final Class<? extends TileEntity> teClass;

    public ONIBaseMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, ModelFile modelFile, int angelOffset, Class<? extends TileEntity> teClass) {
        super(harvestLevel, hardness, resistance, regName, soundType, material, modelFile, angelOffset);
        this.teClass = teClass;
    }

    public ONIBaseMachine(String regName, Properties properties, ModelFile modelFile, int angelOffset, Class<? extends TileEntity> teClass) {
        super(regName, properties, modelFile, angelOffset);
        this.teClass = teClass;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {

//        BlockState state = super.getStateForPlacement(blockItemUseContext);
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());

//        if (state == null) {
//            return null;
//        }
//
//        state.with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//        state.with(FACING, blockItemUseContext.getNearestLookingDirection());

        return this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ONIBaseTE tile = WorldUtils.getTileEntity(ONIBaseTE.class, worldIn, pos);

        if (tile == null) {
            return;
        }

        if (tile instanceof IBoundingBlock) {
            ((IBoundingBlock) tile).onPlace();
        }

        if (tile instanceof ONIIForceStoppable) {
            ONIIForceStoppable forceStoppable = (ONIIForceStoppable) tile;
            if (forceStoppable.isInverted()) {
                forceStoppable.setForceStopped(!worldIn.isBlockPowered(pos));
            } else {
                forceStoppable.setForceStopped(worldIn.isBlockPowered(pos));
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && (!state.isIn(newState.getBlock()) || !newState.hasTileEntity())) {
            TileEntity tile = WorldUtils.getTileEntity(world, pos);
            if (tile instanceof IBoundingBlock) {
                ((IBoundingBlock) tile).onBreak(state);
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        TileEntity tile = WorldUtils.getTileEntity(blockAccess, pos);
        if (tile instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput redstoneOutput = (ONIIHasRedstoneOutput) tile;
            AtomicBoolean isLowTrue = new AtomicBoolean(false);
            AtomicBoolean isHighTrue = new AtomicBoolean(false);

            tile.getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(power -> {
                if ((power.getPower() / power.getCapacity())*100 < redstoneOutput.lowThreshold()) {
                    isLowTrue.set(true);
                }

                if ((power.getPower() / power.getCapacity())*100 > redstoneOutput.highThreshold()) {
                    isHighTrue.set(true);
                }
            });

            return isHighTrue.get() || isLowTrue.get() ? 15 : 0;
        }
        return 0;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (teClass.isInstance(world.getTileEntity(pos))) {
            if (world.getTileEntity(pos) instanceof ONIBaseInvTE) {
                ONIBaseInvTE te = (ONIBaseInvTE) world.getTileEntity(pos);
                if (te != null) {
                    if (te.hasItem()) {
                        ISHandlerHelper.dropInventory(te, world, state, pos, te.getInvSize());
                    }

                    if (te instanceof ONIIModifiable) {
                        ONIIModifiable modifiable = (ONIIModifiable) te;
                        if (modifiable.modContext().containsUpgrades()) {
                            ISHandlerHelper.dropInventory(modifiable.modContext().getModHandler(), world, state, pos, modifiable.modContext().getMaxModAmount());
                        }
                    }
                }
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote() && machineName() != null && !machineName().isEmpty()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ONIIRequireSkillToInteract) {
                ONIIRequireSkillToInteract skill = (ONIIRequireSkillToInteract) tileEntity;
                for (String skillRequired : skill.requiredSkill().keySet()) {
                    if (APIUtils.getLevel(skillRequired, player) < skill.getRequiredLevel(skillRequired)) {
                        player.sendStatusMessage(new TranslationTextComponent("oniutils.message.needLevel", skill.getRequiredLevel(skillRequired), skill.requiredSkill()), true);
                        return ActionResultType.PASS;
                    }
                }
            }
            ItemStack heldItem = player.getHeldItem(hand);
            super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
            if (teClass.isInstance(tileEntity)) {
                if (tileEntity instanceof ONIIModifiable && tileEntity instanceof ONIBaseTE) {
                    if (!heldItem.isEmpty() && heldItem.getItem() instanceof ONIBaseModification) {
                        ONIIModifiable modifiable = (ONIIModifiable) tileEntity;
                        if (modifiable.addMod((ONIBaseTE) tileEntity, heldItem)) {
                            player.swing(hand, true);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }

                if (state.getBlock() instanceof ONIIHasGui) {
                    ONIIHasGui hasGui = (ONIIHasGui) state.getBlock();
                    INamedContainerProvider containerProvider = new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new TranslationTextComponent(machineName());
                        }

                        @Override
                        public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                            return hasGui.container(i, world, pos, playerInventory, playerEntity);
                        }
                    };
                    NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
                }
            } else {
                ONIUtils.LOGGER.warn("Wrong tileEntity type found, failed to create container");
            }
        }
        return ActionResultType.SUCCESS;
    }

    public abstract String machineName();
}
