package wintersteve25.oniutils.common.contents.modules.blocks.oxygen.algae;

import net.minecraft.block.material.Material;
import wintersteve25.oniutils.common.contents.base.ONIBaseMachine;

public class AlgaeDiffuserBlock extends ONIBaseMachine {

    public AlgaeDiffuserBlock() {
        super("Algae Diffuser", Properties.create(Material.IRON));
    }

    @Override
    public boolean doModelGen() {
        return false;
    }
}
