package wintersteve25.oniutils.common.blocks.libs;

import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ONIBaseSixWaysBlock extends SixWayBlock implements IWaterLoggable {
    private final String regName;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ONIBaseSixWaysBlock(Properties p_i48440_1_, String regName) {
        super(2F, p_i48440_1_);
        this.regName = regName;

        registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    public String getRegName() {
        return regName;
    }
}
