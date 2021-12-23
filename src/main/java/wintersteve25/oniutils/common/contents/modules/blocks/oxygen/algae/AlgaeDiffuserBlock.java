package wintersteve25.oniutils.common.contents.modules.blocks.oxygen.algae;

import net.minecraft.block.material.Material;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.contents.base.ONIBaseMachine;

public class AlgaeDiffuserBlock extends ONIBaseMachine implements ONIIHasGeoItem {
    private static final String regName = "Algae Diffuser";

    public AlgaeDiffuserBlock() {
        super(regName, Properties.create(Material.IRON));
    }
}
