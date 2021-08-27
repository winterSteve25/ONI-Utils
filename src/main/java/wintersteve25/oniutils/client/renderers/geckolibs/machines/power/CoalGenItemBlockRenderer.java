package wintersteve25.oniutils.client.renderers.geckolibs.machines.power;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class CoalGenItemBlockRenderer extends GeoItemRenderer<CoalGenItemBlock> {
    public CoalGenItemBlockRenderer() {
        super(ONIConstants.Geo.COAL_GEN_IB);
    }
}
