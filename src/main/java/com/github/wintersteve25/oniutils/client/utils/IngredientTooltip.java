package com.github.wintersteve25.oniutils.client.utils;

import com.github.wintersteve25.oniutils.common.utils.PartialItemIngredient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class IngredientTooltip implements ClientTooltipComponent {
    
    private final PartialItemIngredient ingredient;
    private final ItemStack[] itemStacks;
    private ItemStack itemStackShown;
    private int shownIndex;
    private float countDown;

    public IngredientTooltip(PartialItemIngredient ingredient) {
        this.ingredient = ingredient;
        itemStacks = ingredient.getIngredient().getItems();
        shownIndex = 0;
        itemStackShown = itemStacks.length == 0 ? ItemStack.EMPTY : itemStacks[0];
    }

    @Override
    public void renderImage(Font pFont, int x, int y, PoseStack pPoseStack, ItemRenderer pItemRenderer, int pBlitOffset) {
        pItemRenderer.renderGuiItem(itemStackShown, x, y);

        countDown += Minecraft.getInstance().getDeltaFrameTime();
        if (countDown >= 30) {
            countDown = 0;
            shownIndex += 1;
            shownIndex %= itemStacks.length;
            itemStackShown = itemStacks[shownIndex];
        }
    }

    @Override
    public void renderText(Font pFont, int pX, int pY, Matrix4f pMatrix4f, MultiBufferSource.BufferSource pBufferSource) {
        Component itemName = itemStackShown.getHoverName();
        pFont.drawInBatch(itemName, (float)pX + 18, (float)pY + 4f, -1, true, pMatrix4f, pBufferSource, false, 0, 15728880);
        pFont.drawInBatch(" x" + ingredient.getCount(), (float)pX + 18 + pFont.width(itemName), (float)pY + (getHeight() - 9) / 2f, -1, true, pMatrix4f, pBufferSource, false, 0, 15728880);
    }

    @Override
    public int getHeight() {
        return 17;
    }

    @Override
    public int getWidth(Font pFont) {
        return 18 + pFont.width(itemStackShown.getHoverName()) + pFont.width(" x" + ingredient.getCount());
    }
}
