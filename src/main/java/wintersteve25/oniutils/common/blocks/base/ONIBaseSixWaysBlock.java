package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.SixWayBlock;
import net.minecraft.item.Item;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIBaseSixWaysBlock extends SixWayBlock {
    private final String regName;

    public ONIBaseSixWaysBlock(Properties properties, String regName) {
        super(2, properties);
        this.regName = regName;

        setDefaultState(getStateContainer().getBaseState().with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(UP, Boolean.valueOf(false)).with(DOWN, Boolean.valueOf(false)));
    }

    public String getRegName() {
        return regName;
    }

    public void initBlock(ONIBaseSixWaysBlock b, Item i) {
        ONIBlocks.sixWaysList.put(b, i);
    }
}
