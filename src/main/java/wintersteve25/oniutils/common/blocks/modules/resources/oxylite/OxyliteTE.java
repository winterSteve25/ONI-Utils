package wintersteve25.oniutils.common.blocks.modules.resources.oxylite;

import net.minecraft.tileentity.ITickableTileEntity;
import wintersteve25.oniutils.common.blocks.base.ONIBaseTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class OxyliteTE extends ONIBaseTE implements ITickableTileEntity {
    public OxyliteTE() {
        super(ONIBlocks.OXYLITE_TE.get());
    }

    @Override
    public void tick() {

    }

    @Override
    protected int progress() {
        return 0;
    }
}
