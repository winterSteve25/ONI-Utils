package wintersteve25.oniutils.client.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.TextureElement;

import javax.annotation.Nullable;

public class RenderingHelper {

    public static void bindWidgetsTexture() {
        bindTexture(ONIConstants.Resources.WIDGETS);
    }

    public static void bindTexture(ResourceLocation resourceLocation) {
        Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void renderAnimatedProgressBar(AbstractGui gui, MatrixStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, ONIConstants.Resources.RIGHT_ARROW_BIG.getU(), ONIConstants.Resources.RIGHT_ARROW_BIG.getV(), scaledProgress, ONIConstants.Resources.RIGHT_ARROW_BIG.getHeight());
    }

    public static void renderAnimatedFlame(AbstractGui gui, MatrixStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, ONIConstants.Resources.FLAME.getU(), ONIConstants.Resources.FLAME.getV()+scaledProgress, 14, 14-scaledProgress);
    }

    public static void renderSlotWithHover(AbstractGui gui, MatrixStack matrixStack, int x, int y, int mouseX, int mouseY) {
        bindWidgetsTexture();
        if (mouseX > x && mouseX < x + 17 && mouseY > y && mouseY < y + 17) {
            gui.blit(matrixStack, x, y, ONIConstants.Resources.ITEM_SLOT_HOVER.getU(), ONIConstants.Resources.ITEM_SLOT_HOVER.getV(), ONIConstants.Resources.ITEM_SLOT_HOVER.getWidth(), ONIConstants.Resources.ITEM_SLOT_HOVER.getHeight());
        } else {
            gui.blit(matrixStack, x, y, ONIConstants.Resources.ITEM_SLOT.getU(), ONIConstants.Resources.ITEM_SLOT.getV(), ONIConstants.Resources.ITEM_SLOT.getWidth(), ONIConstants.Resources.ITEM_SLOT.getHeight());
        }
    }

    public static void renderWidget(AbstractGui gui, TextureElement widgetElement, MatrixStack matrixStack, int x, int y) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, widgetElement.getU(), widgetElement.getV(), widgetElement.getWidth(), widgetElement.getHeight());
    }

    public static void renderWidget(TextureElement widgetElement, MatrixStack matrixStack, int x, int y, int blitOffset) {
        bindWidgetsTexture();
        blit(matrixStack, x, y, blitOffset, widgetElement.getU(), widgetElement.getV(), widgetElement.getWidth(), widgetElement.getHeight());
    }

    public static void blit(MatrixStack matrixStack, int x, int y, int blitOffset, int uOffset, int vOffset, int uWidth, int vHeight) {
        blit(matrixStack, x, y, blitOffset, (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256);
    }

    public static void blit(MatrixStack matrixStack, int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth) {
        innerBlit(matrixStack, x, x + uWidth, y, y + vHeight, blitOffset, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    private static void innerBlit(MatrixStack matrixStack, int x1, int x2, int y1, int y2, int blitOffset, int uWidth, int vHeight, float uOffset, float vOffset, int textureWidth, int textureHeight) {
        innerBlit(matrixStack.getLast().getMatrix(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)uWidth) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)vHeight) / (float)textureHeight);
    }

    public static void innerBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(matrix, (float)x1, (float)y2, (float)blitOffset).tex(minU, maxV).endVertex();
        bufferbuilder.pos(matrix, (float)x2, (float)y2, (float)blitOffset).tex(maxU, maxV).endVertex();
        bufferbuilder.pos(matrix, (float)x2, (float)y1, (float)blitOffset).tex(maxU, minV).endVertex();
        bufferbuilder.pos(matrix, (float)x1, (float)y1, (float)blitOffset).tex(minU, minV).endVertex();
        bufferbuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferbuilder);
    }

    public static void renderItemStackInGui(MatrixStack matrixStack, int x, int y, float xScale, float yScale, float zScale, @Nullable ItemStack stackToRender, boolean renderOverlays) {
        if (stackToRender != null) {
            RenderSystem.pushMatrix();
            RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
            RenderSystem.enableDepthTest();
            RenderHelper.enableStandardItemLighting();
            FontRenderer font = Minecraft.getInstance().fontRenderer;
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            renderItemModelIntoGUI(stackToRender, x, y, xScale, yScale, zScale);
            if (renderOverlays) {
                itemRenderer.renderItemOverlayIntoGUI(font, stackToRender, x, y, (String) null);
            }
            RenderSystem.disableBlend();
            RenderHelper.disableStandardItemLighting();
            RenderSystem.popMatrix();
        }
    }

    public static void renderItemStackInGui(MatrixStack matrixStack, int x, int y, @Nullable ItemStack stackToRender, boolean renderOverlays) {
        if (stackToRender != null) {
            RenderSystem.pushMatrix();
            RenderSystem.multMatrix(matrixStack.getLast().getMatrix());
            RenderSystem.enableDepthTest();
            RenderHelper.enableStandardItemLighting();
            FontRenderer font = Minecraft.getInstance().fontRenderer;
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            renderItemModelIntoGUI(stackToRender, x, y, 64, 64, 64);
            if (renderOverlays) {
                itemRenderer.renderItemOverlayIntoGUI(font, stackToRender, x, y, (String) null);
            }
            RenderSystem.disableBlend();
            RenderHelper.disableStandardItemLighting();
            RenderSystem.popMatrix();
        }
    }

    public static void renderItemModelIntoGUI(ItemStack stack, int x, int y) {
        renderItemModelIntoGUI(stack, x, y, Minecraft.getInstance().getItemRenderer());
    }

    public static void renderItemModelIntoGUI(ItemStack stack, int x, int y, ItemRenderer renderer) {
        renderItemModelIntoGUI(stack, x, y, renderer, 16F, 16F, 16F);
    }

    public static void renderItemModelIntoGUI(ItemStack stack, int x, int y, float xScale, float yScale, float zScale) {
        renderItemModelIntoGUI(stack, x, y, Minecraft.getInstance().getItemRenderer(), xScale, yScale, zScale);
    }

    public static void renderItemModelIntoGUI(ItemStack stack, int x, int y, ItemRenderer renderer, float xScale, float yScale, float zScale) {
        renderItemModelIntoGUI(null, stack, x, y, renderer, xScale, yScale, zScale);
    }

    public static void renderItemModelIntoGUI(@Nullable LivingEntity livingEntity, ItemStack stack, int x, int y, ItemRenderer renderer, float xScale, float yScale, float zScale) {
        renderItemModelIntoGUI(stack, x, y, renderer.getItemModelWithOverrides(stack, null, livingEntity), renderer, xScale, yScale, zScale);
    }

    //modified from renderItemModelIntoGUI in ItemRenderer class in vanilla
    public static void renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, ItemRenderer renderer, float xScale, float yScale, float zScale) {
        RenderSystem.pushMatrix();

        Minecraft.getInstance().textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef((float)x, (float)y, 100.0F + renderer.zLevel);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(xScale, yScale, zScale);
        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        boolean flag = !bakedmodel.isSideLit();
        if (flag) {
            RenderHelper.setupGuiFlatDiffuseLighting();
        }

        renderer.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        irendertypebuffer$impl.finish();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupGui3DDiffuseLighting();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }
}
