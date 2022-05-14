package wintersteve25.oniutils.client.renderers.geckolibs.base;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.helpers.ResoureceLocationHelper;

public class GeckolibModelBase<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final ResourceLocation rl1;
    private final ResourceLocation rl2;
    private final ResourceLocation rl3;

    public GeckolibModelBase(String pathModel, String pathTexture, String pathAnimation) {
        this.rl1 = ResoureceLocationHelper.ResourceLocationBuilder.getBuilder()
                .geoModel()
                .addPath(pathModel)
                .build();
        this.rl2 = new ResourceLocation(ONIUtils.MODID, "textures/block/" + pathTexture);
        this.rl3 = new ResourceLocation(ONIUtils.MODID, "animations/" + pathAnimation);
    }

    public GeckolibModelBase(GeckolibModelBase other) {
        this.rl1 = other.rl1;
        this.rl2 = other.rl2;
        this.rl3 = other.rl3;
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
