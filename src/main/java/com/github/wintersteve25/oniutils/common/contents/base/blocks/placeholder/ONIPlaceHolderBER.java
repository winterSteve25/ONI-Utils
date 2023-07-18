package com.github.wintersteve25.oniutils.common.contents.base.blocks.placeholder;

import com.github.wintersteve25.oniutils.client.utils.ColoredRenderTypeBuffer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraftforge.client.model.data.EmptyModelData;

public class ONIPlaceHolderBER implements BlockEntityRenderer<ONIPlaceHolderTE> {
    
    private final BlockRenderDispatcher renderDispatcher;
    
    public ONIPlaceHolderBER(BlockRenderDispatcher renderDispatcher) {
        this.renderDispatcher = renderDispatcher;
    }
    
    @Override
    public void render(ONIPlaceHolderTE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        
        pPoseStack.translate(0.5f, -0.5f, 0.5f);
        Direction direction = pBlockEntity.getBlockState().getValue(DirectionalBlock.FACING);
        rotateBasedOnDirection(pPoseStack, direction);
        pPoseStack.translate(-0.5f, 0.5f, -0.5f);
        pPoseStack.translate(0, -0.5f, 0);

        ColoredRenderTypeBuffer buffer = new ColoredRenderTypeBuffer(Minecraft.getInstance().renderBuffers().bufferSource(), (pBlockEntity.getCompletionPercentage() - 0.1f) + 0.1f, .5f, .86f, .96f);
        renderDispatcher.renderSingleBlock(pBlockEntity.getInPlaceOf().getBlock().defaultBlockState(), pPoseStack, buffer, 15728640, OverlayTexture.RED_OVERLAY_V, EmptyModelData.INSTANCE);
        
        pPoseStack.popPose();
    }

    private void rotateBasedOnDirection(PoseStack poseStack, Direction direction) {
        switch (direction) {
            case SOUTH -> poseStack.mulPose(Vector3f.YP.rotationDegrees(180f));
            case EAST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(-90f));
            case WEST -> poseStack.mulPose(Vector3f.YP.rotationDegrees(90f));
            default -> {}
        }
    }
}
