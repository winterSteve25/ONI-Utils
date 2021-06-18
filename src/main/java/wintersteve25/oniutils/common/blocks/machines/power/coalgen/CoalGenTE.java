package wintersteve25.oniutils.common.blocks.machines.power.coalgen;

import net.minecraft.tileentity.ITickableTileEntity;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class CoalGenTE extends ONIBaseTE implements ITickableTileEntity {
    public CoalGenTE() {
        super(ONIBlocks.COAL_GEN_TE.get());
    }

    @Override
    public void tick() {

    }
}
