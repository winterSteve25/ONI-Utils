package wintersteve25.oniutils.common.contents.base;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Dummy class for the builder
 */
public class ONIBaseLoggableMachine<BE extends BlockEntity> extends ONIBaseMachine<BE> {
    public ONIBaseLoggableMachine(String regName, Properties properties, Class<BE> beClass, BlockEntityType<BE> type) {
        super(regName, properties, beClass, type);
    }

    @Override
    public boolean isFluidLoggable() {
        return true;
    }
}
