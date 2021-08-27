package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TEPosBasedPacket;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class ONIBaseGuiTabRedstone extends ONIBaseGuiTab {

    private SliderPercentageOption slider;
    private Widget sliderWidget;

    private SliderPercentageOption slider2;
    private Widget sliderWidget2;

    private static int lowThreshold = 20;
    private static int highThreshold = 80;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, String title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);

        int i = 40 + (width - 177 - 200) / 2;
        int j = (this.height - 167) / 2;

        slider = new SliderPercentageOption(REDSTONE_LOW, 0, 100, 1, gameSettings -> (double) lowThreshold, (setting, value) -> lowThreshold = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslationTextComponent(REDSTONE_LOW, sliderPercentageOption1.get(gameSettings)));
        sliderWidget = slider.createWidget(Minecraft.getInstance().gameSettings, i, j + 25, 120);

        slider2 = new SliderPercentageOption(REDSTONE_HIGH, 0, 100, 1, gameSettings -> (double) highThreshold, (setting, value) -> highThreshold = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslationTextComponent(REDSTONE_HIGH, sliderPercentageOption1.get(gameSettings)));
        sliderWidget2 = slider2.createWidget(Minecraft.getInstance().gameSettings, i, j + 48, 120);

        this.children.add(sliderWidget);
        this.children.add(sliderWidget2);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        OptionSlider sliderWidge = (OptionSlider) sliderWidget;
        OptionSlider sliderWidge2 = (OptionSlider) sliderWidget2;

        if (sliderWidge.isMouseOver(mouseX, mouseY)) {
            sliderWidge.onClick(mouseX, mouseY);
        }

        if (sliderWidge2.isMouseOver(mouseX, mouseY)) {
            sliderWidge2.onClick(mouseX, mouseY);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            sliderWidget.render(matrixStack, mouseX, mouseY, partialTicks);
            sliderWidget2.render(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.popMatrix();

            //TODO: Make this not janky
            if (this.isDragging()) {
                OptionSlider sliderWidge = (OptionSlider) sliderWidget;
                if (sliderWidge.isMouseOver(mouseX, mouseY)) {
                    sliderWidge.changeSliderValue(mouseX);
                }

                OptionSlider sliderWidge2 = (OptionSlider) sliderWidget2;
                if (sliderWidge2.isMouseOver(mouseX, mouseY)) {
                    sliderWidge2.changeSliderValue(mouseX);
                }
            }
        }
    }

    public ONIBaseGuiTabRedstone create() {
        if (lowThreshold != 20) {
            ONIBaseGuiTabRedstone tab = new ONIBaseGuiTabRedstone();
            tab.setLowCache(lowThreshold);
            if (highThreshold != 80) {
                tab.setHighCache(highThreshold);
            }
            return tab;
        }

        if (highThreshold != 80) {
            ONIBaseGuiTabRedstone tab = new ONIBaseGuiTabRedstone();
            tab.setHighCache(highThreshold);
            if (lowThreshold != 20) {
                tab.setLowCache(lowThreshold);
            }
            return tab;
        }

        return new ONIBaseGuiTabRedstone();
    }

    public void setLowCache(int in) {
        lowThreshold = in;
    }

    public void setHighCache(int in) {
        highThreshold = in;
    }

    @Override
    public void onClose() {
        super.onClose();
        ONINetworking.sendToServer(new TEPosBasedPacket(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_LOW, lowThreshold));
        ONINetworking.sendToServer(new TEPosBasedPacket(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH, highThreshold));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        ONINetworking.sendToServer(new TEPosBasedPacket(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_LOW, lowThreshold));
        ONINetworking.sendToServer(new TEPosBasedPacket(this.container.getTileEntity(), ONIConstants.PacketType.REDSTONE_OUTPUT_HIGH, highThreshold));
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
