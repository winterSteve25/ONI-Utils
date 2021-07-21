package wintersteve25.oniutils.client.renderers.geckolibs.machines.power.coal;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;

public class CoalGenGeckoItemRenderer extends GeoItemRenderer<CoalGenItemBlock> {
    public CoalGenGeckoItemRenderer() {
        super(new CoalGenGeckoItemModel());
    }
}
