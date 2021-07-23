package wintersteve25.oniutils.client.renderers.geckolibs.base;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.oniutils.ONIUtils;

public class GeckolibModelBase<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final ResourceLocation rl1;
    private final ResourceLocation rl2;
    private final ResourceLocation rl3;

    public GeckolibModelBase(String pathModel, String pathTexture, String pathAnimation) {
        this.rl1 = new ResourceLocation(ONIUtils.MODID, "geo/" + pathModel);
        this.rl2 = new ResourceLocation(ONIUtils.MODID, "textures/block/" + pathTexture);
        this.rl3 = new ResourceLocation(ONIUtils.MODID, "animations/" + pathAnimation);
    }

    @Override
    public ResourceLocation getModelLocation(T t) {
        return rl1;
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return rl2;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T t) {
        return rl3;
    }
}
