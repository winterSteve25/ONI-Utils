package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;

public class ONIBaseGuiTabModification extends ONIBaseGuiTab {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/tab_advanced.png");
    private int slots = 6;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, String title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);
        this.slots = containerIn.getModSlotAmount();
    }

    @Override
    public void toggleVisibility() {
        this.container.setModTabOpen(!this.container.isModTabOpen());
        super.toggleVisibility();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            this.mc.getTextureManager().bindTexture(BACKGROUND);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = getGuiLeftTopPosition(this.width, 177) - 147;
            int j = (this.height - 167) / 2;
            this.blit(matrixStack, i, j, 1, 1, 147, 170);

            int slotFixX = 0;
            int slotFixY = 0;

            for (int a = 0; a < slots; a++) {
                if (slotFixX > 100) {
                    slotFixX = 0;
                    slotFixY += 20;
                }
                this.blit(matrixStack, i + 10 + slotFixX, j + 25 + slotFixY, 0, 238, 16, 16);
                slotFixX += 20;
            }

            mc.fontRenderer.func_243246_a(matrixStack, title, i + 10, j + 10, TextFormatting.WHITE.getColor());

            RenderSystem.popMatrix();
        }
    }
}
