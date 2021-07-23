package wintersteve25.oniutils.client.renderers.geckolibs.machines.power;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;
import wintersteve25.oniutils.common.utils.helper.ONIGeoConstants;

public class CoalGenItemBlockRenderer extends GeoItemRenderer<CoalGenItemBlock> {
    public CoalGenItemBlockRenderer() {
        super(ONIGeoConstants.COAL_GEN_IB);
    }
}
