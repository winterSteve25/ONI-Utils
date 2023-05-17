package com.github.wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.ProgressOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import com.github.wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseTE;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIHasRedstoneOutput;
import com.github.wintersteve25.oniutils.common.network.ONINetworking;
import com.github.wintersteve25.oniutils.common.network.PacketUpdateServerBE;
import com.github.wintersteve25.oniutils.common.utils.ONIConstants;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ONIBaseGuiTabRedstone extends ONIBaseGuiTab {

    private ForgeSlider slider1;
    private ForgeSlider slider2;

    private int lowThreshold = 20;
    private int highThreshold = 80;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, Component title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);

        int i = 40 + (width - 177 - 200) / 2;
        int j = (this.height - 167) / 2;

        BlockEntity tile = containerIn.getTileEntity();
        CompoundTag nbt = tile.getUpdateTag();

        if (nbt.contains("low_threshold")) {
            lowThreshold = nbt.getInt("low_threshold");
        }
        if (nbt.contains("high_threshold")) {
            highThreshold = nbt.getInt("high_threshold");
        }

        slider1 = new ForgeSlider(i, j + 25, 120, 20, new TranslatableComponent(REDSTONE_LOW), TextComponent.EMPTY, 0, 100, lowThreshold, 1, 0, true) {
            @Override
            protected void applyValue() {
                lowThreshold = slider1.getValueInt();
            }
        };
        
        slider2 = new ForgeSlider(i, j + 48, 120, 20, new TranslatableComponent(REDSTONE_HIGH), TextComponent.EMPTY, 0, 100, highThreshold, 1, 0, true) {
            @Override
            protected void applyValue() {
                highThreshold = slider2.getValueInt();
            }
        };
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            matrixStack.pushPose();
            slider1.render(matrixStack, mouseX, mouseY, partialTicks);
            slider2.render(matrixStack, mouseX, mouseY, partialTicks);
            matrixStack.popPose();

            if (this.isDragging()) {
                if (slider1.isMouseOver(mouseX, mouseY)) {
                    slider1.mouseDragged(mouseX, mouseY, 0, 0, 0);
                }

                if (slider2.isMouseOver(mouseX, mouseY)) {
                    slider2.mouseDragged(mouseX, mouseY, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void toggleVisibility() {
        super.toggleVisibility();
        if (isVisible()) {
            addWidget(slider1);
            addWidget(slider2);
        } else {
            removeWidget(slider1);
            removeWidget(slider2);
        }
    }

    @Override
    public void onClose() {
        updateData();
        super.onClose();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isVisible()) {
            updateData();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void updateData() {
        ONINetworking.sendToServer(new PacketUpdateServerBE(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_LOW, lowThreshold));
        ONINetworking.sendToServer(new PacketUpdateServerBE(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH, highThreshold));
        updateClientThreshold();
    }

    private void updateClientThreshold() {
        ONIBaseTE clientTE = this.container.getTileEntity();
        if (clientTE.getLevel().isClientSide()) {
            if (clientTE instanceof ONIIHasRedstoneOutput) {
                ONIIHasRedstoneOutput output = (ONIIHasRedstoneOutput) clientTE;
                output.setLowThreshold(lowThreshold);
                output.setHighThreshold(highThreshold);
            }
        }
    }
}