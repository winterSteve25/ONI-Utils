package wintersteve25.oniutils.common.contents.base.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;

public class ONIBaseSixWaysBlock extends ONIBaseBlock {
    protected static final BooleanProperty NORTH = PipeBlock.NORTH;
    protected static final BooleanProperty EAST = PipeBlock.EAST;
    protected static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    protected static final BooleanProperty WEST = PipeBlock.WEST;
    protected static final BooleanProperty UP = PipeBlock.UP;
    protected static final BooleanProperty DOWN = PipeBlock.DOWN;

    public ONIBaseSixWaysBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}