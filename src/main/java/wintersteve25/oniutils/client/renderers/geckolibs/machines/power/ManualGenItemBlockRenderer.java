package wintersteve25.oniutils.client.renderers.geckolibs.machines.power;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.utils.helper.ONIGeoConstants;

public class ManualGenItemBlockRenderer extends GeoItemRenderer<ManualGenItemBlock> {
    public ManualGenItemBlockRenderer() {
        super(ONIGeoConstants.MANUAL_GEN_IB);
    }
}
