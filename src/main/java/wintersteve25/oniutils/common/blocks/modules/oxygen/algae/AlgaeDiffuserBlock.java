package wintersteve25.oniutils.common.blocks.modules.oxygen.algae;

import net.minecraft.block.material.Material;
import wintersteve25.oniutils.common.blocks.base.ONIBaseAnimatedMachine;

public class AlgaeDiffuserBlock extends ONIBaseAnimatedMachine {
    private static final String regName = "Algae Diffuser";

    public AlgaeDiffuserBlock() {
        super(Properties.create(Material.IRON), regName, null);
    }


}
