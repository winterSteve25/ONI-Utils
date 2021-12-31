package wintersteve25.oniutils.common.contents.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;

public class ONIBaseSixWaysBlock extends ONIBaseBlock {
    protected static final BooleanProperty NORTH = SixWayBlock.NORTH;
    protected static final BooleanProperty EAST = SixWayBlock.EAST;
    protected static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    protected static final BooleanProperty WEST = SixWayBlock.WEST;
    protected static final BooleanProperty UP = SixWayBlock.UP;
    protected static final BooleanProperty DOWN = SixWayBlock.DOWN;

    public ONIBaseSixWaysBlock(String regName, Properties properties) {
        super(regName, properties);
        setDefaultState(getStateContainer().getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(UP, Boolean.valueOf(false)).with(DOWN, Boolean.valueOf(false)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.fillStateContainer(stateBuilder);
        stateBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}