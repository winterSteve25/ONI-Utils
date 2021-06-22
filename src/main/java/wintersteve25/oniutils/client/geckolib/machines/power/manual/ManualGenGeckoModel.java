package wintersteve25.oniutils.client.geckolib.machines.power.manual;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;

public class ManualGenGeckoModel extends AnimatedGeoModel<ManualGenTE> {
    @Override
    public ResourceLocation getModelLocation(ManualGenTE te) {
        return new ResourceLocation(ONIUtils.MODID, "geo/manual_gen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ManualGenTE te) {
        return new ResourceLocation(ONIUtils.MODID, "textures/block/machines/manual_gen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ManualGenTE te) {
        return new ResourceLocation(ONIUtils.MODID, "animations/manual_gen.animation.json");
    }
}
