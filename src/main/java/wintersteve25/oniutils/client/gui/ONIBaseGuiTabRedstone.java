package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;

public class ONIBaseGuiTabRedstone extends ONIBaseGuiTab {

    private SliderPercentageOption slider;
    private Widget sliderWidget;
    private int lowThreshold = 20;
    private int highThreshold = 80;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, String title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);
        this.mc.keyboardListener.enableRepeatEvents(true);

        int i = getGuiLeftTopPosition(this.width, 177) - 147 + 8;
        int j = (this.height - 167) / 2;

        slider = new SliderPercentageOption(REDSTONE_LOW, 0, 100, 1, gameSettings -> (double) this.lowThreshold, (setting, value) -> this.lowThreshold = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslationTextComponent(REDSTONE_LOW, sliderPercentageOption1.get(gameSettings)));
        sliderWidget = slider.createWidget(Minecraft.getInstance().gameSettings, i, j + 25, 120);
        this.children.add(sliderWidget);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        OptionSlider sliderWidge = (OptionSlider) sliderWidget;
        if (sliderWidge.isMouseOver(mouseX, mouseY) && sliderWidge.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            sliderWidge.onDrag(mouseX, mouseY, dragX, dragY);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        OptionSlider sliderWidge = (OptionSlider) sliderWidget;
        sliderWidge.onClick(mouseX, mouseY);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            sliderWidget.render(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.popMatrix();
        }
    }
}
