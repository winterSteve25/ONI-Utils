package com.github.wintersteve25.oniutils.common.contents.modules.blocks.power.coal;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import com.github.wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;
import com.github.wintersteve25.oniutils.client.utils.RenderingHelper;
import com.github.wintersteve25.oniutils.common.contents.base.builders.ONIAbstractContainer;
import com.github.wintersteve25.oniutils.common.utils.ONIConstants;

import javax.annotation.Nonnull;

public class CoalGenGui extends ONIBaseGuiContainer<ONIAbstractContainer> {

    public CoalGenGui(ONIAbstractContainer container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);

        RenderingHelper.bindWidgetsTexture();
        RenderingHelper.renderWidget(this, ONIConstants.Resources.ITEM_SLOT, matrixStack, this.leftPos + 54, this.topPos + 31);
        RenderingHelper.renderWidget(this, ONIConstants.Resources.RIGHT_ARROW_BIG_BG, matrixStack, this.leftPos + 79, this.topPos + 36);
        RenderingHelper.renderWidget(this, ONIConstants.Resources.FLAME_BG, matrixStack, this.leftPos + 56, this.topPos + 51);
        RenderingHelper.renderWidget(this, ONIConstants.Resources.POWER_BAR_BG, matrixStack, this.leftPos + 105, this.topPos + 24);
        RenderingHelper.renderWidget(this, ONIConstants.Resources.BATTERY_BG, matrixStack, this.leftPos + 105, this.topPos + 6);

        //power
        int b = getScaledPower(49);
        RenderingHelper.renderAnimatedPowerBar(this, matrixStack, this.leftPos + 106, this.topPos + 25 + b, b);

        if (menu.getWorking() == 1) {
            int p = getScaledProgress(22);
            int f = getScaledProgress(13);

            //burn
            RenderingHelper.renderAnimatedFlame(this, matrixStack, this.leftPos + 56, this.topPos + 51 + f, f);

            //progress
            RenderingHelper.renderAnimatedProgressBar(this, matrixStack, this.leftPos + 78, this.topPos + 36, p);

            //progress tooltip
            super.renderCustomToolTip(matrixStack, mouseX, mouseY, this.leftPos, this.topPos, 79, 30, 103, 45, new TranslatableComponent("oniutils.gui.machines.progress", menu.getProgress()/10));
        }

        int power = menu.getPower();
        super.powerToolTip(matrixStack, power, mouseX, mouseY, this.leftPos, this.topPos);
    }

    @Override
    protected boolean hasRedstoneOutputButton() {
        return true;
    }
}