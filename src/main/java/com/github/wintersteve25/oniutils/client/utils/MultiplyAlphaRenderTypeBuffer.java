package com.github.wintersteve25.oniutils.client.utils;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

// Copied from Building Gadgets source code, credits to the folks working on that project, thanks a lot
// https://github.com/Direwolf20-MC/BuildingGadgets/blob/master/src/main/java/com/direwolf20/buildinggadgets/client/renderer/OurRenderTypes.java#L122
public class MultiplyAlphaRenderTypeBuffer implements MultiBufferSource {
    private final MultiBufferSource inner;
    private final float constantAlpha;

    public MultiplyAlphaRenderTypeBuffer(MultiBufferSource inner, float constantAlpha) {
        this.inner = inner;
        this.constantAlpha = constantAlpha;
    }

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        RenderType localType = type;
        if (localType instanceof RenderType.CompositeRenderType) {
            // all of this requires a lot of AT's so be aware of that on ports
            ResourceLocation texture = ((RenderStateShard.TextureStateShard) ((RenderType.CompositeRenderType) localType).state.textureState).texture.orElse(InventoryMenu.BLOCK_ATLAS);
            localType = RenderType.entityTranslucentCull(texture);
        } else if (localType.toString().equals(Sheets.translucentCullBlockSheet().toString())) {
            localType = Sheets.translucentCullBlockSheet();
        }

        return new MultiplyAlphaVertexBuilder(this.inner.getBuffer(localType), this.constantAlpha);
    }

    /**
     * Required for modifying the alpha value.
     */
    public static class MultiplyAlphaVertexBuilder implements VertexConsumer {
        private final VertexConsumer inner;
        private final float constantAlpha;

        public MultiplyAlphaVertexBuilder(VertexConsumer inner, float constantAlpha) {
            this.inner = inner;
            this.constantAlpha = constantAlpha;
        }

        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return inner.vertex(x, y, z);
        }

        @Override
        public VertexConsumer vertex(Matrix4f matrixIn, float x, float y, float z) {
            return inner.vertex(matrixIn, x, y, z);
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return inner.color(red, green, blue, (int) (alpha * constantAlpha));
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return inner.uv(u, v);
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return inner.overlayCoords(u, v);
        }


        @Override
        public VertexConsumer uv2(int u, int v) {
            return inner.uv2(u, v);
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return inner.normal(x, y, z);
        }

        @Override
        public VertexConsumer normal(Matrix3f matrixIn, float x, float y, float z) {
            return inner.normal(matrixIn, x, y, z);
        }

        @Override
        public void endVertex() {
            this.inner.endVertex();
        }

        @Override
        public void defaultColor(int p_166901_, int p_166902_, int p_166903_, int p_166904_) {
            inner.defaultColor(p_166901_, p_166902_, p_166903_, p_166904_);
        }

        @Override
        public void unsetDefaultColor() {
            inner.unsetDefaultColor();
        }
    }
}
