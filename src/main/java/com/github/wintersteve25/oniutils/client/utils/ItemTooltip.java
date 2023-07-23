package com.github.wintersteve25.oniutils.client.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class ItemTooltip implements ClientTooltipComponent {
    
    private final ItemStack itemStack;
    
    public ItemTooltip(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    @Override
    public void renderImage(Font pFont, int x, int y, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
        pItemRenderer.renderGuiItem(itemStack, x, y);
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font pFont) {
        return 16;
    }
}
