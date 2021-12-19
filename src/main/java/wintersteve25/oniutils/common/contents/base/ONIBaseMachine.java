package wintersteve25.oniutils.common.contents.base;

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
import net.minecraftforge.fml.network.NetworkHooks;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.api.*;
import wintersteve25.oniutils.api.functional.ITETypeProvider;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.contents.modules.modifications.ONIModification;
import wintersteve25.oniutils.common.utils.helpers.ISHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class ONIBaseMachine extends ONIBaseDirectional {
    private final Class<? extends TileEntity> teClass;

    // block builder properties
    private ITETypeProvider tileEntityType;
    private ONIIHasGui gui;

    public ONIBaseMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, Class<? extends TileEntity> teClass) {
        super(harvestLevel, hardness, resistance, regName, soundType, material);
        this.teClass = teClass;
    }

    public ONIBaseMachine(String regName, Properties properties, Class<? extends TileEntity> teClass) {
        super(regName, properties);
        this.teClass = teClass;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {

        BlockState state = super.getStateForPlacement(blockItemUseContext);
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());

        if (state == null) {
            return null;
        }

//        state.with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
        state.with(FACING, blockItemUseContext.getNearestLookingDirection());

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

        tile.onPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && (!state.isIn(newState.getBlock()) || !newState.hasTileEntity())) {
            TileEntity tile = WorldUtils.getTileEntity(world, pos);
            if (tile instanceof IBoundingBlock) {
                ((IBoundingBlock) tile).onBreak(state);
            }
            if (teClass.isInstance(tile) && tile instanceof ONIBaseTE) {
                ((ONIBaseTE) tile).onBroken(state, world, pos, newState, isMoving);
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
        if (teClass.isInstance(tile) && tile instanceof ONIBaseTE) {
            ((ONIBaseTE) tile).getWeakPower(blockState, blockAccess, pos, side);
        }

        return 0;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (teClass.isInstance(world.getTileEntity(pos))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ONIBaseTE) {
                ONIBaseTE baseTE = (ONIBaseTE) tileEntity;
                baseTE.onHarvested(world, pos, state, player);
                if (baseTE instanceof ONIBaseInvTE) {
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
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ONIIRequireSkillToInteract) {
                ONIIRequireSkillToInteract skill = (ONIIRequireSkillToInteract) tileEntity;
                for (String skillRequired : skill.requiredSkill().keySet()) {
                    if (APIUtils.getLevel(skillRequired, player) < skill.getRequiredLevel(skillRequired)) {
                        player.sendStatusMessage(new TranslationTextComponent("oniutils.message.needLevel", skill.getRequiredLevel(skillRequired), skill.requiredSkill()), true);
                        return ActionResultType.FAIL;
                    }
                }
            }
            ItemStack heldItem = player.getHeldItem(hand);
            super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
            if (teClass.isInstance(tileEntity)) {
                if (tileEntity instanceof ONIIModifiable && tileEntity instanceof ONIBaseTE) {
                    ONIBaseTE baseTE = (ONIBaseTE) tileEntity;
                    baseTE.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
                    if (!heldItem.isEmpty() && heldItem.getItem() instanceof ONIModification) {
                        ONIIModifiable modifiable = (ONIIModifiable) tileEntity;
                        if (modifiable.addMod((ONIBaseTE) tileEntity, heldItem)) {
                            player.swing(hand, true);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }

                if (gui != null || this instanceof ONIIHasGui) {
                    if (gui == null) {
                        gui = (ONIIHasGui) this;
                    }
                    if (gui.machineName() != null) {
                        INamedContainerProvider containerProvider = new INamedContainerProvider() {
                            @Override
                            public ITextComponent getDisplayName() {
                                return gui.machineName();
                            }

                            @Override
                            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                                return gui.container(i, world, pos, playerInventory, playerEntity);
                            }
                        };
                        NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
                    }
                }
            } else {
                ONIUtils.LOGGER.warn("Wrong tileEntity type found, failed to create container");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        TileEntity tile = world.getTileEntity(pos);
        if (teClass.isInstance(tile) && tile instanceof ONIBaseTE) {
            return ((ONIBaseTE) tile).canConnectRedstone(state, world, pos, side);
        }
        return super.canConnectRedstone(state, world, pos, side);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return getTileEntityType() == null ? super.createTileEntity(state, world) : tileEntityType.createTEType(state, world).create();
    }

    @Nullable
    public ONIIHasGui getGui() {
        return gui == null ? this instanceof ONIIHasGui ? (ONIIHasGui) this : null : gui;
    }

    public void setGui(ONIIHasGui gui) {
        this.gui = gui;
    }

    public ITETypeProvider getTileEntityType() {
        return tileEntityType;
    }

    public void setTileEntityType(ITETypeProvider tileEntityType) {
        this.tileEntityType = tileEntityType;
    }

    public Class<? extends TileEntity> getTeClass() {
        return teClass;
    }
}
