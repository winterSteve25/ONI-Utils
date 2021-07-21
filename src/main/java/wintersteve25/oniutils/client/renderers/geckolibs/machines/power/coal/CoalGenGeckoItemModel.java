package wintersteve25.oniutils.client.renderers.geckolibs.machines.power.coal;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;

public class CoalGenGeckoItemModel extends AnimatedGeoModel<CoalGenItemBlock> {
    @Override
    public ResourceLocation getModelLocation(CoalGenItemBlock coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "geo/coal_gen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CoalGenItemBlock coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "textures/block/machines/coal_gen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CoalGenItemBlock coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "animations/coal_gen.animation.json");
    }
}
