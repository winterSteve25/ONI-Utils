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
        gui.blit(matrixStack, x, y, ONIConstants.Resources.RIGHT_ARROW_BIG.getU(), ONIConstants.Resources.RIGHT_ARROW_BIG.getV(), scaledProgress, ONIConstants.Resources.RIGHT_ARROW_BIG.getHeight());
    }

    public static void renderAnimatedFlame(GuiComponent gui, PoseStack matrixStack, int x, int y, int scaledProgress) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, ONIConstants.Resources.FLAME.getU(), ONIConstants.Resources.FLAME.getV()+scaledProgress, 14, 14-scaledProgress);
    }

    public static void renderSlotWithHover(GuiComponent gui, PoseStack matrixStack, int x, int y, int mouseX, int mouseY) {
        bindWidgetsTexture();
        if (mouseX > x && mouseX < x + 17 && mouseY > y && mouseY < y + 17) {
            gui.blit(matrixStack, x, y, ONIConstants.Resources.ITEM_SLOT_HOVER.getU(), ONIConstants.Resources.ITEM_SLOT_HOVER.getV(), ONIConstants.Resources.ITEM_SLOT_HOVER.getWidth(), ONIConstants.Resources.ITEM_SLOT_HOVER.getHeight());
        } else {
            gui.blit(matrixStack, x, y, ONIConstants.Resources.ITEM_SLOT.getU(), ONIConstants.Resources.ITEM_SLOT.getV(), ONIConstants.Resources.ITEM_SLOT.getWidth(), ONIConstants.Resources.ITEM_SLOT.getHeight());
        }
    }

    public static void renderWidget(GuiComponent gui, TextureElement widgetElement, PoseStack matrixStack, int x, int y) {
        bindWidgetsTexture();
        gui.blit(matrixStack, x, y, widgetElement.getU(), widgetElement.getV(), widgetElement.getWidth(), widgetElement.getHeight());
    }
}
