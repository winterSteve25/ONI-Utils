package wintersteve25.oniutils.common.blocks.base;

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
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance));
        this.regName = null;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance));
        this.regName = regName;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance));
        this.regName = regName;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance));
        this.regName = regName;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    public ONIBaseDirectional(Properties properties, String regName) {
        super(properties);
        this.regName = regName;
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.with(FACING, mirror.mirror(blockState.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.getDefaultState().with(FACING, blockItemUseContext.getFace());
    }
}
