package wintersteve25.oniutils.common.contents.modules.blocks.power.cables;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.contents.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.contents.base.enums.EnumCableTypes;
import wintersteve25.oniutils.common.init.ONIItems;

public class PowerCableBlock extends ONIBaseSixWaysBlock {
    private final EnumCableTypes type;
    private final VoxelShape BASE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7, 6.85, 7, 9, 9.35, 9), VoxelShapes.or(Block.makeCuboidShape(6.75, 8.2, 6.75, 9.25, 9.2, 9.25), VoxelShapes.or(Block.makeCuboidShape(7.5, 7.6, 6.5, 8.5, 8.6, 9.5), VoxelShapes.or(Block.makeCuboidShape(6.5, 7.6, 7.5, 9.5, 8.6, 8.5), VoxelShapes.or(Block.makeCuboidShape(7.5, 6.6, 7.5, 8.5, 9.6, 8.5), Block.makeCuboidShape(6.75, 7, 6.75, 9.25, 8, 9.25))))), IBooleanFunction.OR);
    private final VoxelShape NORTH_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 7.7, 2.3, 8.4, 8.5, 6.5), Block.makeCuboidShape(7.5, 7.6, 0, 8.5, 8.6, 2.5), IBooleanFunction.OR);
    private final VoxelShape SOUTH_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_180);
    private final VoxelShape EAST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_90);
    private final VoxelShape WEST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.COUNTERCLOCKWISE_90);
    private final VoxelShape UP_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 9.5, 7.699999999999999, 8.4, 13.7, 8.5), Block.makeCuboidShape(7.5, 13.5, 7.6, 8.5, 16, 8.6), IBooleanFunction.OR);
    private final VoxelShape DOWN_SIDE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(7.6, 2.3, 7.6, 8.4, 6.5, 8.4), Block.makeCuboidShape(7.5, 0, 7.5, 8.5, 2.5, 8.5), IBooleanFunction.OR);

    public PowerCableBlock(EnumCableTypes cableTypes) {
        this(cableTypes, Properties.create(Material.IRON).doesNotBlockMovement().notSolid().hardnessAndResistance(0.4f, 0.9f));
    }

    public PowerCableBlock(EnumCableTypes cableTypes, Properties properties) {
        super(cableTypes.getName(), properties);
        type = cableTypes;
        setDoModelGen(false);
    }

    @Override
    public boolean isFluidLoggable() {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getWorld(), context.getPos());
    }

    public BlockState makeConnections(World blockReader, BlockPos pos) {
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

        boolean down = block instanceof PowerCableBlock || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
        boolean up = block1 instanceof PowerCableBlock || te1 != null && te1.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
        boolean north = block2 instanceof PowerCableBlock || te2 != null && te2.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
        boolean east = block3 instanceof PowerCableBlock || te3 != null && te3.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
        boolean south = block4 instanceof PowerCableBlock || te4 != null && te4.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
        boolean west = block5 instanceof PowerCableBlock || te5 != null && te5.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
//
//        if (blockReader.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) {
//            Circuit circuit = Circuit.createCircuit(blockReader, type.getPowerTransferLimit());
//            if (circuit != null) {
//                WorldCircuits cap = blockReader.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).resolve().get();
//
//                if (block instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.down()));
//                }
//
//                if (te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te.getPos());
//                }
//
//                if (block1 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.up()));
//                }
//
//                if (te1 != null && te1.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te1.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te1.getPos());
//                }
//
//                if (block2 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.north()));
//                }
//
//                if (te2 != null && te2.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te2.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te2.getPos());
//                }
//
//                if (block3 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.east()));
//                }
//
//                if (te3 != null && te3.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te3.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te3.getPos());
//                }
//
//                if (block4 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.south()));
//                }
//
//                if (te4 != null && te4.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te4.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te4.getPos());
//                }
//
//                if (block5 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.west()));
//                }
//
//                if (te5 != null && te5.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te5.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te5.getPos());
//                }
//
//                cap.replaceAndAddCircuits(circuit);
//            }
//        }

        return this.getDefaultState().with(DOWN, down)
                .with(UP, up)
                .with(NORTH, north)
                .with(EAST, east)
                .with(SOUTH, south)
                .with(WEST, west);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        boolean flag;
        TileEntity te = worldIn.getTileEntity(facingPos);
        flag = facingState.getBlock() instanceof PowerCableBlock || te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent();
//        if (((World) worldIn).getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) {
//            Circuit circuit = Circuit.createCircuit(((World) worldIn), type.getPowerTransferLimit());
//            if (circuit != null) {
//                WorldCircuits cap = ((World) worldIn).getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).resolve().get();
//
//                if (facingState.getBlock() instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(((World) worldIn), cap.getCircuitWithCableAtPos(facingPos));
//                }
//
//                if (te != null && te.getCapability(PlasmaCapability.POWER_CAPABILITY).isPresent()) {
//                    IPlasma plasma = te.getCapability(PlasmaCapability.POWER_CAPABILITY).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te.getPos());
//                }
//
//                cap.replaceAndAddCircuits(circuit);
//            }
//        }
        return stateIn.with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(facing), flag);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = BASE;

        if (context.hasItem(ONIItems.WIRE_CUTTER)) {
            return VoxelShapes.fullCube();
        }

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

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float value = super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
        if (player.getHeldItemMainhand().getItem() == ONIItems.WIRE_CUTTER) {
            value*=4;
        }
        return value;
    }
}