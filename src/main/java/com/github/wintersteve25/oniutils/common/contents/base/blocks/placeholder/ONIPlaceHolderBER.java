package com.github.wintersteve25.oniutils.common.contents.base.blocks.placeholder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ONIPlaceHolderBER implements BlockEntityRenderer<ONIPlaceHolderTE> {
    @Override
    public void render(ONIPlaceHolderTE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack inputItem = new ItemStack(Items.ICE);
            pPoseStack.pushPose();
            itemRenderer.renderStatic(inputItem, ItemTransforms.TransformType.GROUND, 15728880, OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 0);
            pPoseStack.popPose();
    }
}
