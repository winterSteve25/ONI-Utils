package wintersteve25.oniutils.common.blocks.modules.power.cables;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class WireBlock extends ONIBaseSixWaysBlock {
    private final EnumCableTypes type;
    private final VoxelShape BASE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 6.85, 7, 9, 9.35, 9), VoxelShapes.or(Block.makeCuboidShape(6.75, 8.2, 6.75, 9.25, 9.2, 9.25), VoxelShapes.or(Block.makeCuboidShape(7.5, 7.6, 6.5, 8.5, 8.6, 9.5), VoxelShapes.or(Block.makeCuboidShape(6.5, 7.6, 7.5, 9.5, 8.6, 8.5), VoxelShapes.or(Block.makeCuboidShape(7.5, 6.6, 7.5, 8.5, 9.6, 8.5), Block.makeCuboidShape(6.75, 7, 6.75, 9.25, 8, 9.25))))), IBooleanFunction.OR);
    private final VoxelShape NORTH_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 7.7, 2.3, 8.4, 8.5, 6.5), Block.makeCuboidShape(7.5, 7.6, 0, 8.5, 8.6, 2.5), IBooleanFunction.OR);
    private final VoxelShape SOUTH_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_180);
    private final VoxelShape EAST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_90);
    private final VoxelShape WEST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.COUNTERCLOCKWISE_90);
    private final VoxelShape UP_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 9.5, 7.699999999999999, 8.4, 13.7, 8.5), Block.makeCuboidShape(7.5, 13.5, 7.6, 8.5, 16, 8.6), IBooleanFunction.OR);
    private final VoxelShape DOWN_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 2.3, 7.6, 8.4, 6.5, 8.4), Block.makeCuboidShape(7.5, 0, 7.5, 8.5, 2.5, 8.5), IBooleanFunction.OR);

    public WireBlock(EnumCableTypes cableTypes) {
        super(Properties.create(Material.IRON).doesNotBlockMovement().notSolid(), cableTypes.getName());
        type = cableTypes;
    }

    public WireBlock(EnumCableTypes cableTypes, Properties properties) {
        super(properties, cableTypes.getName());
        type = cableTypes;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    public static int coord(double v) {
        return v < 0.375D ? -1 : v > 0.625D ? 1 : 0;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isSneaking()) {
            Vector3d hitResult = hit.getHitVec();
            double x = pos.getX() - hitResult.x;
            double y = pos.getY() - hitResult.y;
            double z = pos.getZ() - hitResult.z;

            System.out.println(x + "," + y + "," + z);
        }
        return ActionResultType.PASS;
    }

//    private Direction getDirectionFromVec(double x, double y, double z) {
//        if ()
//    }

    public BlockState disconnect(IBlockReader blockReader, BlockPos pos, Direction direction) {
        BlockState state = blockReader.getBlockState(pos);
        switch (direction) {
            case SOUTH:
                if (state.hasProperty(SOUTH)) {
                    state.with(SOUTH, false);
                }
                break;
            case UP:
                if (state.hasProperty(UP)) {
                    state.with(UP, false);
                }
                break;
            case DOWN:
                if (state.hasProperty(DOWN)) {
                    state.with(DOWN, false);
                }
                break;
            case EAST:
                if (state.hasProperty(EAST)) {
                    state.with(EAST, false);
                }
                break;
            case WEST:
                if (state.hasProperty(WEST)) {
                    state.with(WEST, false);
                }
                break;
            default:
                if (state.hasProperty(NORTH)) {
                    state.with(NORTH, false);
                }
                break;
        }
        return state;
    }

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.down()).getBlock();
        Block block1 = blockReader.getBlockState(pos.up()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();

        TileEntity te = blockReader.getTileEntity(pos.down());
        TileEntity te1 = blockReader.getTileEntity(pos.up());
        TileEntity te2 = blockReader.getTileEntity(pos.north());
        TileEntity te3 = blockReader.getTileEntity(pos.east());
        TileEntity te4 = blockReader.getTileEntity(pos.south());
        TileEntity te5 = blockReader.getTileEntity(pos.west());

        switch (type) {
            case HEAVIWATTS:
                return this.getDefaultState().with(DOWN, block == this || block == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(UP, block1 == this || block1 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te1 != null && te1.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(NORTH, block2 == this || block2 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te2 != null && te2.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(EAST, block3 == this || block3 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te3 != null && te3.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(SOUTH, block4 == this || block4 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te4 != null && te4.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(WEST, block5 == this || block5 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK || te5 != null && te5.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent());
            case CONDUCTIVE:
                return this.getDefaultState().with(DOWN, block == this || block == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(UP, block1 == this || block1 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te1 != null && te1.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(NORTH, block2 == this || block2 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te2 != null && te2.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(EAST, block3 == this || block3 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te3 != null && te3.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(SOUTH, block4 == this || block4 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te4 != null && te4.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(WEST, block5 == this || block5 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK || te5 != null && te5.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent());
            default:
                return this.getDefaultState().with(DOWN, block == this || block == ONIBlocks.WIRE_BLOCK || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(UP, block1 == this || block1 == ONIBlocks.WIRE_BLOCK || te1 != null && te1.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(NORTH, block2 == this || block2 == ONIBlocks.WIRE_BLOCK || te2 != null && te2.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(EAST, block3 == this || block3 == ONIBlocks.WIRE_BLOCK || te3 != null && te3.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(SOUTH, block4 == this || block4 == ONIBlocks.WIRE_BLOCK || te4 != null && te4.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent())
                        .with(WEST, block5 == this || block5 == ONIBlocks.WIRE_BLOCK || te5 != null && te5.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent());
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        boolean flag;

        TileEntity te = worldIn.getTileEntity(facingPos);

        switch (type) {
            case HEAVIWATTS:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.HEAVI_WATT_WIRE_BLOCK) || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
                break;
            case CONDUCTIVE:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.CONDUCTIVE_WIRE_BLOCK) || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
                break;
            default:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.WIRE_BLOCK) || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
                break;
        }

        return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), flag);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = BASE;

        if (state.get(NORTH)) {
            shape = VoxelShapes.or(shape, NORTH_SIDE);
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapes.or(shape, SOUTH_SIDE);
        }
        if (state.get(EAST)) {
            shape = VoxelShapes.or(shape, EAST_SIDE);
        }
        if (state.get(WEST)) {
            shape = VoxelShapes.or(shape, WEST_SIDE);
        }
        if (state.get(UP)) {
            shape = VoxelShapes.or(shape, UP_SIDE);
        }
        if (state.get(DOWN)) {
            shape = VoxelShapes.or(shape, DOWN_SIDE);
        }

        return shape;
    }
}