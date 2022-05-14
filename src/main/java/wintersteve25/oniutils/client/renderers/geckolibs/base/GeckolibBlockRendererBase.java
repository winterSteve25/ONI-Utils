package wintersteve25.oniutils.client.renderers.geckolibs.base;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GeckolibBlockRendererBase<T extends BlockEntity & IAnimatable> extends GeoBlockRenderer<T> {
    public GeckolibBlockRendererBase(BlockEntityRendererProvider.Context rendererDispatcherIn, AnimatedGeoModel<T> modelProvider) {
        super(rendererDispatcherIn, modelProvider);
    }

//    @Override
//    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
//        return RenderType.entityTranslucent(getTextureLocation(animatable));
//    }
}
