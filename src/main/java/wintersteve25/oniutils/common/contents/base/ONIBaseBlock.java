package wintersteve25.oniutils.common.contents.base;

import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.api.*;
import wintersteve25.oniutils.api.functional.IRenderTypeProvider;
import wintersteve25.oniutils.api.functional.ITETypeProvider;
import wintersteve25.oniutils.api.functional.IVoxelShapeProvider;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.utils.helpers.ISHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ONIBaseBlock extends Block implements ONIIStateFluidLoggable, ONIIRegistryObject<Block> {

    private final String regName;

    // block builder properties
    private IVoxelShapeProvider hitBox;
    private IRenderTypeProvider renderType;
    private Supplier<Item> blockItem;
    private boolean doModelGen = true;
    private boolean doStateGen = false;
    private boolean doLangGen = true;
    private boolean doLootTableGen = true;

    private Class<? extends TileEntity> teClass;
    private ITETypeProvider tileEntityType;

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName) {
        this(harvestLevel, hardness, resistance, regName, SoundType.STONE);
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        this(harvestLevel, hardness, resistance, regName, soundType, Material.ROCK);
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        this(regName, Properties.create(material).harvestLevel(harvestLevel).hardnessAndResistance(hardness, resistance).sound(soundType));
    }

    public ONIBaseBlock(String regName, Properties properties) {
        super(properties);
        this.regName = regName;
//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

    @Override
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return this.hasTileEntity(state) ? PushReaction.BLOCK : super.getPushReaction(state);
    }

//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        super.fillStateContainer(builder);
//        builder.add(this.getFluidLoggedProperty());
//    }

//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
//        return this.getDefaultState().with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return hitBox == null ? super.getShape(state, worldIn, pos, context) : hitBox.createShape(state, worldIn, pos, context);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return renderType == null ? super.getRenderType(state) : renderType.createRenderType(state);
    }

    @Override
    public boolean doModelGen() {
        return doModelGen;
    }

    @Override
    public boolean doStateGen() {
        return doStateGen;
    }

    @Override
    public boolean doLangGen() {
        return doLangGen;
    }

    @Override
    public boolean doLootTableGen() {
        return doLootTableGen;
    }

    public ONIBaseBlock setDoModelGen(boolean doModelGen) {
        this.doModelGen = doModelGen;
        return this;
    }

    public ONIBaseBlock setDoStateGen(boolean doStateGen) {
        this.doStateGen = doStateGen;
        return this;
    }

    public ONIBaseBlock setDoLangGen(boolean doLangGen) {
        this.doLangGen = doLangGen;
        return this;
    }

    public ONIBaseBlock setDoLootTableGen(boolean doLootTableGen) {
        this.doLootTableGen = doLootTableGen;
        return this;
    }

    @Override
    public Block get() {
        return this;
    }

    @Override
    public String getRegName() {
        return regName;
    }

    public IVoxelShapeProvider getHitBox() {
        return hitBox;
    }

    public void setHitBox(IVoxelShapeProvider hitBox) {
        this.hitBox = hitBox;
    }

    public IRenderTypeProvider getRenderType() {
        return renderType;
    }

    public void setRenderType(IRenderTypeProvider renderType) {
        this.renderType = renderType;
    }

    public Supplier<Item> getBlockItem() {
        return blockItem;
    }

    public void setBlockItem(Supplier<Item> blockItem) {
        this.blockItem = blockItem;
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && (!state.isIn(newState.getBlock()) || !newState.hasTileEntity())) {
            TileEntity tile = WorldUtils.getTileEntity(world, pos);
            if (tile instanceof IBoundingBlock) {
                ((IBoundingBlock) tile).onBreak(state);
            }
            if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
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
        if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
            ((ONIBaseTE) tile).getWeakPower(blockState, blockAccess, pos, side);
        }

        return 0;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (isCorrectTe(world.getTileEntity(pos))) {
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
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        TileEntity tile = world.getTileEntity(pos);
        if (isCorrectTe(tile) && tile instanceof ONIBaseTE) {
            return ((ONIBaseTE) tile).canConnectRedstone(state, world, pos, side);
        }
        return super.canConnectRedstone(state, world, pos, side);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return getTileEntityType() == null ? super.createTileEntity(state, world) : tileEntityType.createTEType(state, world).create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return getTileEntityType() != null && getTeClass() != null;
    }

    public ITETypeProvider getTileEntityType() {
        return tileEntityType;
    }

    public void setTileEntityType(ITETypeProvider tileEntityType) {
        this.tileEntityType = tileEntityType;
    }

    public boolean isCorrectTe(TileEntity tile) {
        return getTeClass() != null && getTeClass().isInstance(tile);
    }

    public Class<? extends TileEntity> getTeClass() {
        return teClass;
    }

    public void setTeClass(Class<? extends TileEntity> teClass) {
        this.teClass = teClass;
    }
}
