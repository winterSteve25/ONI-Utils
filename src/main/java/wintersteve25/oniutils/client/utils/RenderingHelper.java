package wintersteve25.oniutils.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import wintersteve25.oniutils.common.utils.ONIConstants;
import wintersteve25.oniutils.common.utils.TextureElement;

public class RenderingHelper {

    public static void bindWidgetsTexture() {
        bindTexture(ONIConstants.Resources.WIDGETS);
    }

    public static void bindTexture(ResourceLocation resourceLocation) {
        RenderSystem.setShaderTexture(0, resourceLocation);
    }

    public static void renderAnimatedProgressBar(GuiComponent gui, PoseStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        var arrow = ONIConstants.Resources.RIGHT_ARROW_BIG;
        gui.blit(matrixStack, x, y, arrow.getU(), arrow.getV(), scaledProgress, arrow.getHeight());
    }

    public static void renderAnimatedFlame(GuiComponent gui, PoseStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        var flame = ONIConstants.Resources.FLAME;
        gui.blit(matrixStack, x, y, flame.getU(), flame.getV()+scaledProgress, flame.getWidth(), flame.getHeight()-scaledProgress);
    }

    public static void renderAnimatedPowerBar(GuiComponent gui, PoseStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        var bar = ONIConstants.Resources.POWER_BAR;
        gui.blit(matrixStack, x, y, bar.getU(), bar.getV()+scaledProgress, bar.getWidth(), bar.getHeight()-scaledProgress);
    }

    public static void renderSlot(GuiComponent gui, PoseStack matrixStack, int x, int y, int mouseX, int mouseY) {
        bindWidgetsTexture();
        var slot = ONIConstants.Resources.ITEM_SLOT;
        gui.blit(matrixStack, x, y, slot.getU(), slot.getV(), slot.getWidth(), slot.getHeight());
    }

    public static void renderWidget(GuiComponent gui, TextureElement widgetElement, PoseStack matrixStack, int x, int y) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, widgetElement.getU(), widgetElement.getV(), widgetElement.getWidth(), widgetElement.getHeight());
    }
}
