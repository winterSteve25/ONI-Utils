package com.github.wintersteve25.oniutils.client.renderers.entities;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;

public class EmptyEntityRenderer<T extends Entity> extends EntityRenderer<T> {
    public EmptyEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}
