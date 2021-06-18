package wintersteve25.oniutils.common.blocks.libs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.init.ONIBlocks;

@SuppressWarnings("deprecation")
public class ONIBaseDirectional extends DirectionalBlock {
    private final String regName;

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(hardness, resistance));
        this.regName = null;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(hardness, resistance));
        this.regName = regName;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).strength(hardness, resistance));
        this.regName = regName;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(Properties.of(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).strength(hardness, resistance));
        this.regName = regName;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public String getRegName() {
        return this.regName;
    }

    public void initRock(ONIBaseDirectional b) {
        ONIBlocks.direList.add(b);
    }

    public void initRockNoDataGen(ONIBaseDirectional b) {
        ONIBlocks.direListNoDataGen.add(b);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.defaultBlockState().setValue(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());
    }
}
