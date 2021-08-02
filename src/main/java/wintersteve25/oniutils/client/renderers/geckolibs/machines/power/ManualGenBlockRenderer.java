package wintersteve25.oniutils.client.renderers.geckolibs.machines.power;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibBlockRendererBase;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;
import wintersteve25.oniutils.common.utils.helper.ONIGeoConstants;

public class ManualGenBlockRenderer extends GeckolibBlockRendererBase<ManualGenTE> {
    public ManualGenBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, ONIGeoConstants.MANUAL_GEN_TE);
    }

    @Override
    public void render(ManualGenTE tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
    }
}
