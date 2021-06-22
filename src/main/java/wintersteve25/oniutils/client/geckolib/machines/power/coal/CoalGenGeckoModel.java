package wintersteve25.oniutils.client.geckolib.machines.power.coal;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenTE;

public class CoalGenGeckoModel extends AnimatedGeoModel<CoalGenTE> {
    @Override
    public ResourceLocation getModelLocation(CoalGenTE coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "geo/coal_gen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CoalGenTE coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "textures/block/machines/coal_gen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CoalGenTE coalGenTE) {
        return new ResourceLocation(ONIUtils.MODID, "animations/coal_gen.animation.json");
    }
}
