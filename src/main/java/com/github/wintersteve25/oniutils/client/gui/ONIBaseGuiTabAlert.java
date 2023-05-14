package com.github.wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class ONIBaseGuiTabAlert extends ONIBaseGuiTab {

    public static final TranslatableComponent WARNING_DURABILITY = new TranslatableComponent(ONIBaseGuiTab.WARNING_DURABILITY);
    public static final TranslatableComponent WARNING_TEMPERATURE = new TranslatableComponent(ONIBaseGuiTab.WARNING_TEMPERATURE);
    public static final TranslatableComponent WARNING_GAS_PRESSURE = new TranslatableComponent(ONIBaseGuiTab.WARNING_GAS_PRESSURE);
    public static final TranslatableComponent WARNING_WRONG_GAS = new TranslatableComponent(ONIBaseGuiTab.WARNING_WRONG_GAS);
    public static final TranslatableComponent WARNING_ALL_CLEAR = new TranslatableComponent(ONIBaseGuiTab.WARNING_ALL_CLEAR);

    private final List<TranslatableComponent> currentWarnings = new ArrayList<>();

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 100);
            renderWarnings(currentWarnings.size(), matrixStack);
            matrixStack.popPose();
        }
    }

    public void addWarning(TranslatableComponent add) {
        currentWarnings.add(add);
    }

    public void removeWarning(TranslatableComponent remove) {
        currentWarnings.remove(remove);
    }

    //ugly hack but it works :/
    public void renderWarnings(int warningCount, PoseStack mx) {
        int ogPos = ((this.height - 167) / 2) + 26;
        int toAddPos = 12;

        switch (warningCount) {
            case 0:
                mc.font.drawShadow(mx, WARNING_ALL_CLEAR, (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.GRAY.getColor());
                break;
            case 1:
                mc.font.drawShadow(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.RED.getColor());
                break;
            case 2:
                mc.font.drawShadow(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos, ChatFormatting.RED.getColor());
                break;
            case 3:
                mc.font.drawShadow(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(2), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos * 2, ChatFormatting.RED.getColor());
                break;
            case 4:
                mc.font.drawShadow(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(2), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos * 2, ChatFormatting.RED.getColor());
                mc.font.drawShadow(mx, currentWarnings.get(3), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos + toAddPos * 3, ChatFormatting.RED.getColor());
                break;
        }
    }
}