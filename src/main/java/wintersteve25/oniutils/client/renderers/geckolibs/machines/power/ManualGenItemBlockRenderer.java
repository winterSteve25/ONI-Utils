package wintersteve25.oniutils.client.renderers.geckolibs.machines.power;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.common.contents.modules.blocks.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class ManualGenItemBlockRenderer extends GeoItemRenderer<ManualGenItemBlock> {
    public ManualGenItemBlockRenderer() {
        super(ONIConstants.Geo.MANUAL_GEN_IB);
    }
}
