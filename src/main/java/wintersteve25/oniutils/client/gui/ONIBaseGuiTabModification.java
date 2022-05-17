package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.client.utils.RenderingHelper;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;

public class ONIBaseGuiTabModification extends ONIBaseGuiTab {
    private int slots = 6;
    private static int errorCoolDown = 0;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, Component title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);
        this.slots = containerIn.getModSlotAmount();
    }

    @Override
    public void toggleVisibility() {
        this.container.setModTabOpen(!this.container.isModTabOpen());
        super.toggleVisibility();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            matrixStack.pushPose();
            RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);

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
                RenderingHelper.renderSlot(this, matrixStack, i + 10 + slotFixX, j + 25 + slotFixY, mouseX, mouseY);
                slotFixX += 20;
            }

            // title
            mc.font.drawShadow(matrixStack, title, i + 10, j + 10, ChatFormatting.WHITE.getColor());

            // draw error
            if (errorCoolDown > 0) {
                matrixStack.pushPose();
                matrixStack.scale(0.8f, 0.8f, 0.8f);
                if (mouseX > getGuiLeftTopPosition(this.width, 177)) {
                    mc.font.drawShadow(matrixStack, new TranslatableComponent("oniutils.gui.machines.upgradeNotSupported"), i + 45, j, ChatFormatting.RED.getColor());
                } else {
                    mc.font.drawShadow(matrixStack, new TranslatableComponent("oniutils.gui.machines.upgradeNotSupported"), mouseX, mouseY, ChatFormatting.RED.getColor());
                }
                matrixStack.popPose();
            }
            errorCoolDown--;

            matrixStack.popPose();
        }
    }

    public static void addError() {
        errorCoolDown = 200;
    }
}