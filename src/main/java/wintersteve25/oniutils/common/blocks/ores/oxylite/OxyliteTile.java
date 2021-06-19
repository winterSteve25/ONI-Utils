package wintersteve25.oniutils.common.blocks.ores.oxylite;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class OxyliteTile extends ONIBaseTE implements ITickableTileEntity {
    public OxyliteTile() {
        super(ONIBlocks.OxyliteTE.get());
    }

    @Override
    public void tick() {

    }
}
