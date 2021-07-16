package wintersteve25.oniutils.common.blocks.base.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ONIBaseGuiTabInfo extends ONIBaseGuiTab {

    protected TranslationTextComponent ENERGY;
    protected TranslationTextComponent PRODUCING_ENERGY;
    protected TranslationTextComponent CONSUMING_ENERGY;
    protected TranslationTextComponent PROGRESS;
    protected TranslationTextComponent PRODUCING_GAS;
    protected TranslationTextComponent CONSUMING_GAS;
    protected TranslationTextComponent PRODUCING_LIQUID;
    protected TranslationTextComponent CONSUMING_LIQUID;

    protected List<TranslationTextComponent> currentText = new ArrayList<>();

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            renderTexts(matrixStack);
            RenderSystem.popMatrix();
        }
    }

    public void updateText() {
        ENERGY = new TranslationTextComponent(ONIBaseGuiTab.ENERGY, container.getEnergy());
        PRODUCING_ENERGY = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_ENERGY);
        CONSUMING_ENERGY = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_ENERGY);
        PROGRESS = new TranslationTextComponent(ONIBaseGuiTab.PROGRESS);
        PRODUCING_GAS = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_GAS);
        CONSUMING_GAS = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_GAS);
        PRODUCING_LIQUID = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_LIQUID);
        CONSUMING_LIQUID = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_LIQUID);
    }

    public void renderTexts(MatrixStack mx) {
        int ogPos = ((this.height - 167) / 2) + 26;
        int toAddPos = 12;

        mc.fontRenderer.func_243246_a(mx, ENERGY, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, PRODUCING_ENERGY, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, CONSUMING_ENERGY, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*2, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, PRODUCING_GAS, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*3, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, CONSUMING_GAS, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*4, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, PRODUCING_LIQUID, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*5, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, CONSUMING_LIQUID, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*6, TextFormatting.GRAY.getColor());
        mc.fontRenderer.func_243246_a(mx, PROGRESS, (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*7, TextFormatting.GRAY.getColor());

    }
}
