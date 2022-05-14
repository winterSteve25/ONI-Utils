package wintersteve25.oniutils.common.contents.modules.blocks.power.cables;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import wintersteve25.oniutils.common.contents.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.contents.base.enums.EnumCableTypes;
import wintersteve25.oniutils.common.init.ONICapabilities;
import wintersteve25.oniutils.common.init.ONIItems;

public class PowerCableBlock extends ONIBaseSixWaysBlock {
    private final EnumCableTypes type;
    private final VoxelShape BASE = Shapes.join(Block.box(7, 6.85, 7, 9, 9.35, 9), Shapes.or(Block.box(6.75, 8.2, 6.75, 9.25, 9.2, 9.25), Shapes.or(Block.box(7.5, 7.6, 6.5, 8.5, 8.6, 9.5), Shapes.or(Block.box(6.5, 7.6, 7.5, 9.5, 8.6, 8.5), Shapes.or(Block.box(7.5, 6.6, 7.5, 8.5, 9.6, 8.5), Block.box(6.75, 7, 6.75, 9.25, 8, 9.25))))), BooleanOp.OR);
    private final VoxelShape NORTH_SIDE = Shapes.join(Block.box(7.6, 7.7, 2.3, 8.4, 8.5, 6.5), Block.box(7.5, 7.6, 0, 8.5, 8.6, 2.5), BooleanOp.OR);
    private final VoxelShape SOUTH_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_180);
    private final VoxelShape EAST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.CLOCKWISE_90);
    private final VoxelShape WEST_SIDE = VoxelShapeUtils.rotate(NORTH_SIDE, Rotation.COUNTERCLOCKWISE_90);
    private final VoxelShape UP_SIDE = Shapes.join(Block.box(7.6, 9.5, 7.699999999999999, 8.4, 13.7, 8.5), Block.box(7.5, 13.5, 7.6, 8.5, 16, 8.6), BooleanOp.OR);
    private final VoxelShape DOWN_SIDE = Shapes.join(Block.box(7.6, 2.3, 7.6, 8.4, 6.5, 8.4), Block.box(7.5, 0, 7.5, 8.5, 2.5, 8.5), BooleanOp.OR);

    public PowerCableBlock(EnumCableTypes cableTypes) {
        this(cableTypes, Properties.of(Material.METAL).noCollission().noOcclusion().strength(0.4f, 0.9f));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.makeConnections(context.getLevel(), context.getClickedPos());
    }

    public BlockState makeConnections(Level blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.below()).getBlock();
        Block block1 = blockReader.getBlockState(pos.above()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();

        BlockEntity te = blockReader.getBlockEntity(pos.below());
        BlockEntity te1 = blockReader.getBlockEntity(pos.above());
        BlockEntity te2 = blockReader.getBlockEntity(pos.north());
        BlockEntity te3 = blockReader.getBlockEntity(pos.east());
        BlockEntity te4 = blockReader.getBlockEntity(pos.south());
        BlockEntity te5 = blockReader.getBlockEntity(pos.west());

        boolean down = block instanceof PowerCableBlock || te != null && te.getCapability(ONICapabilities.PLASMA).isPresent();
        boolean up = block1 instanceof PowerCableBlock || te1 != null && te1.getCapability(ONICapabilities.PLASMA).isPresent();
        boolean north = block2 instanceof PowerCableBlock || te2 != null && te2.getCapability(ONICapabilities.PLASMA).isPresent();
        boolean east = block3 instanceof PowerCableBlock || te3 != null && te3.getCapability(ONICapabilities.PLASMA).isPresent();
        boolean south = block4 instanceof PowerCableBlock || te4 != null && te4.getCapability(ONICapabilities.PLASMA).isPresent();
        boolean west = block5 instanceof PowerCableBlock || te5 != null && te5.getCapability(ONICapabilities.PLASMA).isPresent();
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
//                if (te != null && te.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te.getPos());
//                }
//
//                if (block1 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.up()));
//                }
//
//                if (te1 != null && te1.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te1.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te1.getPos());
//                }
//
//                if (block2 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.north()));
//                }
//
//                if (te2 != null && te2.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te2.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te2.getPos());
//                }
//
//                if (block3 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.east()));
//                }
//
//                if (te3 != null && te3.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te3.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te3.getPos());
//                }
//
//                if (block4 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.south()));
//                }
//
//                if (te4 != null && te4.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te4.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te4.getPos());
//                }
//
//                if (block5 instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(blockReader, cap.getCircuitWithCableAtPos(pos.west()));
//                }
//
//                if (te5 != null && te5.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te5.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te5.getPos());
//                }
//
//                cap.replaceAndAddCircuits(circuit);
//            }
//        }

        return this.defaultBlockState().setValue(DOWN, down)
                .setValue(UP, up)
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        boolean flag;
        BlockEntity te = worldIn.getBlockEntity(facingPos);
        flag = facingState.getBlock() instanceof PowerCableBlock || te != null && te.getCapability(ONICapabilities.PLASMA).isPresent();
//        if (((World) worldIn).getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) {
//            Circuit circuit = Circuit.createCircuit(((World) worldIn), type.getPowerTransferLimit());
//            if (circuit != null) {
//                WorldCircuits cap = ((World) worldIn).getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).resolve().get();
//
//                if (facingState.getBlock() instanceof PowerCableBlock) {
//                    circuit = circuit.mergeCircuits(((World) worldIn), cap.getCircuitWithCableAtPos(facingPos));
//                }
//
//                if (te != null && te.getCapability(ONICapabilities.PLASMA).isPresent()) {
//                    IPlasma plasma = te.getCapability(ONICapabilities.PLASMA).resolve().get();
//                    circuit.addToCircuit(plasma.getTileType(), te.getPos());
//                }
//
//                cap.replaceAndAddCircuits(circuit);
//            }
//        }
        return stateIn.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(facing), flag);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape shape = BASE;

        if (context.isHoldingItem(ONIItems.WIRE_CUTTER)) {
            return Shapes.block();
        }

        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, NORTH_SIDE);
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SIDE);
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, EAST_SIDE);
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, WEST_SIDE);
        }
        if (state.getValue(UP)) {
            shape = Shapes.or(shape, UP_SIDE);
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, DOWN_SIDE);
        }

        return shape;
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter worldIn, BlockPos pos) {
        float value = super.getDestroyProgress(state, player, worldIn, pos);
        if (player.getMainHandItem().getItem() == ONIItems.WIRE_CUTTER) {
            value*=4;
        }
        return value;
    }
}