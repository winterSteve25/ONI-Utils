package wintersteve25.oniutils.common.items.base.modifications;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.client.utils.RenderingHelper;

public class ONIModificationGUI extends Screen {

    private final ItemStack modification;

    private SliderPercentageOption slider;
    private Widget sliderWidget;

    private final int maxBonus;
    private static int bonus = 20;

    public ONIModificationGUI(ItemStack modification, int maxBonus) {
        super(new TranslationTextComponent(""));
        this.modification = modification;
        this.maxBonus = maxBonus;
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        int i = (this.width - 150) / 2;
        int j = (this.height - 167) / 2;

        slider = new SliderPercentageOption("oniutils.gui.items.modification.bonus", -maxBonus, maxBonus, 1, gameSettings -> (double) bonus, (setting, value) -> bonus = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslationTextComponent("oniutils.gui.items.modification.bonus", sliderPercentageOption1.get(gameSettings)));
        sliderWidget = slider.createWidget(Minecraft.getInstance().gameSettings, i, j, 120);

        this.children.add(sliderWidget);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 100.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - 150) / 2;
        int j = (this.height - 167) / 2;
        RenderingHelper.renderItemStackInGui(matrixStack, i, j, 32, 32, 32, modification, false);
        sliderWidget.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.popMatrix();
    }
}
