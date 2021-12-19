package wintersteve25.oniutils.common.contents.modules.resources.oxylite;

import mekanism.common.tile.base.TileEntityUpdateable;
import net.minecraft.tileentity.ITickableTileEntity;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class OxyliteTE extends TileEntityUpdateable implements ITickableTileEntity {
    public OxyliteTE() {
        super(ONIBlocks.TileEntityBounded.OXYLITE_TE.get());
    }

    @Override
    public void tick() {

    }
}
