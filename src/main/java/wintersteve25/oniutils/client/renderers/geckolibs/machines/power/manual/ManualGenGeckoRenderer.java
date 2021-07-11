package wintersteve25.oniutils.client.renderers.geckolibs.machines.power.manual;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;

public class ManualGenGeckoRenderer extends GeoBlockRenderer<ManualGenTE> {
    public ManualGenGeckoRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new ManualGenGeckoModel());
    }

    @Override
    public RenderType getRenderType(ManualGenTE animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(getTextureLocation(animatable));
    }
}
