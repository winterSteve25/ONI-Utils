package wintersteve25.oniutils.client.renderers.entities;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EmptyEntityRenderer<T extends Entity> extends EntityRenderer<T> {
    public EmptyEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public boolean shouldRender(T livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
