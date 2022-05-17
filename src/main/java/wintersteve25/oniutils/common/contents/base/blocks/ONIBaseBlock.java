package wintersteve25.oniutils.common.contents.base.blocks;

import mekanism.common.block.states.IStateFluidLoggable;
import mekanism.common.util.WorldUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.common.contents.base.interfaces.ONIIForceStoppable;
import wintersteve25.oniutils.common.contents.base.interfaces.functional.IRenderTypeProvider;
import wintersteve25.oniutils.common.contents.base.interfaces.functional.IVoxelShapeProvider;
import wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIIBoundingBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ONIBaseBlock extends Block implements IStateFluidLoggable {

    // block builder properties
    private IVoxelShapeProvider hitBox;
    private IRenderTypeProvider renderType;

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance) {
        this(harvestLevel, hardness, resistance, SoundType.STONE);
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, SoundType soundType) {
        this(harvestLevel, hardness, resistance, soundType, Material.STONE);
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, SoundType soundType, Material material) {
        this(Properties.of(material).strength(hardness, resistance).sound(soundType));
    }

    public ONIBaseBlock(Properties properties) {
        super(properties);
        if (isFluidLoggable()) {
            BlockState state = this.getStateDefinition().any();
            state = setState(state, Fluids.EMPTY);
            this.registerDefaultState(state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        if (isFluidLoggable()) {
            builder.add(this.getFluidLoggedProperty());
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockItemUseContext) {
        if (isFluidLoggable()) {
            BlockState state = super.getStateForPlacement(blockItemUseContext);
            FluidState fluidState = blockItemUseContext.getLevel().getFluidState(blockItemUseContext.getClickedPos());
            state = setState(state, fluidState.getType());
            return state;
        }
        return super.getStateForPlacement(blockItemUseContext);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (isFluidLoggable()) {
            return this.getFluid(state);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor world, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (isFluidLoggable()) {
            this.updateFluids(state, world, currentPos);
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return getHitBox() == null ? super.getShape(state, worldIn, pos, context) : getHitBox().createShape(state, worldIn, pos, context);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return getRenderType() == null ? super.getRenderShape(state) : getRenderType().createRenderType(state);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ONIBaseTE tile = WorldUtils.getTileEntity(ONIBaseTE.class, worldIn, pos);

        if (tile == null) {
            return;
        }

        if (tile instanceof ONIIBoundingBlock block) {
            block.onPlace();
        }

        if (tile instanceof ONIIForceStoppable forceStoppable) {
            if (forceStoppable.isInverted()) {
                forceStoppable.setForceStopped(!worldIn.hasNeighborSignal(pos));
            } else {
                forceStoppable.setForceStopped(worldIn.hasNeighborSignal(pos));
            }
        }

        tile.onPlacedBy(worldIn, pos, state, placer, stack);
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

    public boolean isFluidLoggable() {
        return false;
    }
}