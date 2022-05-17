package wintersteve25.oniutils.common.contents.base.blocks;

import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Dummy class for the builder
 */
public class ONIBaseLoggableMachine<BE extends BlockEntity> extends ONIBaseMachine<BE> {
    public ONIBaseLoggableMachine(Properties properties, Class<BE> beClass, TileEntityTypeRegistryObject<BE> type) {
        super(properties, beClass, type);
    }

    @Override
    public boolean isFluidLoggable() {
        return true;
    }
}
