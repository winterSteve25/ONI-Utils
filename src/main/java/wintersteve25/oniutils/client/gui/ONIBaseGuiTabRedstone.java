package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.ProgressOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import wintersteve25.oniutils.common.contents.base.ONIBaseTE;
import wintersteve25.oniutils.api.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.PacketUpdateBE;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class ONIBaseGuiTabRedstone extends ONIBaseGuiTab {

    private AbstractWidget sliderWidget;
    private AbstractWidget sliderWidget2;

    private int lowThreshold = 20;
    private int highThreshold = 80;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, Component title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);

        int i = 40 + (width - 177 - 200) / 2;
        int j = (this.height - 167) / 2;

        BlockEntity tile = containerIn.getTileEntity();
        CompoundTag nbt = tile.getUpdateTag();

        if (nbt.getInt("low_threshold") != 0) {
            lowThreshold = nbt.getInt("low_threshold");
        }
        if (nbt.getInt("high_threshold") != 0) {
            highThreshold = nbt.getInt("high_threshold");
        }

        ProgressOption slider = new ProgressOption(REDSTONE_LOW, 0, 100, 1, gameSettings -> (double) lowThreshold, (setting, value) -> lowThreshold = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslatableComponent(REDSTONE_LOW, sliderPercentageOption1.get(gameSettings)));
        sliderWidget = slider.createButton(Minecraft.getInstance().options, i, j + 25, 120);

        ProgressOption slider2 = new ProgressOption(REDSTONE_HIGH, 0, 100, 1, gameSettings -> (double) highThreshold, (setting, value) -> highThreshold = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslatableComponent(REDSTONE_HIGH, sliderPercentageOption1.get(gameSettings)));
        sliderWidget2 = slider2.createButton(Minecraft.getInstance().options, i, j + 48, 120);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isVisible()) {
            SliderButton sliderWidge = (SliderButton) sliderWidget;
            SliderButton sliderWidge2 = (SliderButton) sliderWidget2;

            if (sliderWidge.isMouseOver(mouseX, mouseY)) {
                sliderWidge.onClick(mouseX, mouseY);
            }

            if (sliderWidge2.isMouseOver(mouseX, mouseY)) {
                sliderWidge2.onClick(mouseX, mouseY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 100);
            sliderWidget.render(matrixStack, mouseX, mouseY, partialTicks);
            sliderWidget2.render(matrixStack, mouseX, mouseY, partialTicks);
            matrixStack.popPose();

            if (this.isDragging()) {
                SliderButton sliderWidge = (SliderButton) sliderWidget;
                if (sliderWidge.isMouseOver(mouseX, mouseY)) {
                    sliderWidge.setValueFromMouse(mouseX);
                }

                SliderButton sliderWidge2 = (SliderButton) sliderWidget2;
                if (sliderWidge2.isMouseOver(mouseX, mouseY)) {
                    sliderWidge2.setValueFromMouse(mouseX);
                }
            }
        }
    }

    @Override
    public void toggleVisibility() {
        super.toggleVisibility();
        if (isVisible()) {
            addWidget(sliderWidget);
            addWidget(sliderWidget2);
        } else {
            removeWidget(sliderWidget);
            removeWidget(sliderWidget2);
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isVisible()) {
            ONINetworking.sendToServer(new PacketUpdateBE(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_LOW, lowThreshold));
            ONINetworking.sendToServer(new PacketUpdateBE(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH, highThreshold));
            updateClientThreshold();
        }
        return super.mouseReleased(mouseX, mouseY, button);
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