package wintersteve25.oniutils.common.blocks.modules.power.cables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class WireBlock extends ONIBaseSixWaysBlock implements ONIIHasGeoItem {
    private final EnumCableTypes type;

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

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.down()).getBlock();
        Block block1 = blockReader.getBlockState(pos.up()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();

        switch (type) {
            case HEAVIWATTS:
                return this.getDefaultState().with(DOWN, Boolean.valueOf(block == this || block == ONIBlocks.HEAVI_WATT_WIRE_BLOCK)).with(UP, Boolean.valueOf(block1 == this || block1 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK)).with(NORTH, Boolean.valueOf(block2 == this || block2 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK)).with(EAST, Boolean.valueOf(block3 == this || block3 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK)).with(SOUTH, Boolean.valueOf(block4 == this || block4 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK)).with(WEST, Boolean.valueOf(block5 == this || block5 == ONIBlocks.HEAVI_WATT_WIRE_BLOCK));
            case CONDUCTIVE:
                return this.getDefaultState().with(DOWN, Boolean.valueOf(block == this || block == ONIBlocks.CONDUCTIVE_WIRE_BLOCK)).with(UP, Boolean.valueOf(block1 == this || block1 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK)).with(NORTH, Boolean.valueOf(block2 == this || block2 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK)).with(EAST, Boolean.valueOf(block3 == this || block3 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK)).with(SOUTH, Boolean.valueOf(block4 == this || block4 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK)).with(WEST, Boolean.valueOf(block5 == this || block5 == ONIBlocks.CONDUCTIVE_WIRE_BLOCK));
            default:
                return this.getDefaultState().with(DOWN, Boolean.valueOf(block == this || block == ONIBlocks.WIRE_BLOCK)).with(UP, Boolean.valueOf(block1 == this || block1 == ONIBlocks.WIRE_BLOCK)).with(NORTH, Boolean.valueOf(block2 == this || block2 == ONIBlocks.WIRE_BLOCK)).with(EAST, Boolean.valueOf(block3 == this || block3 == ONIBlocks.WIRE_BLOCK)).with(SOUTH, Boolean.valueOf(block4 == this || block4 == ONIBlocks.WIRE_BLOCK)).with(WEST, Boolean.valueOf(block5 == this || block5 == ONIBlocks.WIRE_BLOCK));
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        boolean flag;

        switch (type) {
            case HEAVIWATTS:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.HEAVI_WATT_WIRE_BLOCK);
                break;
            case CONDUCTIVE:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.CONDUCTIVE_WIRE_BLOCK);
                break;
            default:
                flag = facingState.getBlock() == this || facingState.isIn(ONIBlocks.WIRE_BLOCK);
                break;
        }

        return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(flag));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getShape(state, worldIn, pos, context);
    }
}