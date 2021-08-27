package wintersteve25.oniutils.client.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RenderingHelper {
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
