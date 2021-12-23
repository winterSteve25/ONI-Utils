package wintersteve25.oniutils.client.gui;

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

    protected List<TranslationTextComponent> initializedTexts = new ArrayList<>();

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
        initializedTexts.clear();

        if (container.hasProgress()) {
            PROGRESS = new TranslationTextComponent(ONIBaseGuiTab.PROGRESS, container.getProgress()/10);
            initializedTexts.add(PROGRESS);
        }
        if (container.hasPower()) {
            ENERGY = new TranslationTextComponent(ONIBaseGuiTab.ENERGY, container.getPower());
            initializedTexts.add(ENERGY);
            if (container.isPowerProducer()) {
                PRODUCING_ENERGY = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_ENERGY, container.getProducingPower());
                initializedTexts.add(PRODUCING_ENERGY);
            }
            if (container.isPowerConsumer()) {
                CONSUMING_ENERGY = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_ENERGY, container.getConsumingPower());
                initializedTexts.add(CONSUMING_ENERGY);
            }
        }
        if (container.isGasProducer()) {
            PRODUCING_GAS = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_GAS, container.getProducingGas());
            initializedTexts.add(PRODUCING_GAS);
        }
        if (container.isGasConsumer()) {
            CONSUMING_GAS = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_GAS, container.getConsumingGas());
            initializedTexts.add(CONSUMING_GAS);
        }
        if (container.isLiquidProducer()) {
            PRODUCING_LIQUID = new TranslationTextComponent(ONIBaseGuiTab.PRODUCING_LIQUID, container.getProducingLiquid());
            initializedTexts.add(PRODUCING_LIQUID);
        }
        if (container.isLiquidConsumer()) {
            CONSUMING_LIQUID = new TranslationTextComponent(ONIBaseGuiTab.CONSUMING_LIQUID, container.getConsumingLiquid());
            initializedTexts.add(CONSUMING_LIQUID);
        }
    }

    public void renderTexts(MatrixStack mx) {
        int ogPos = ((this.height - 167) / 2) + 26;
        int toAddPos = 12;

        for (int i = 0; i < initializedTexts.size(); i++) {
            if (i == 0) {
                mc.fontRenderer.func_243246_a(mx, initializedTexts.get(i), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, TextFormatting.GRAY.getColor());
            } else {
                mc.fontRenderer.func_243246_a(mx, initializedTexts.get(i), (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*i, TextFormatting.GRAY.getColor());
            }
        }

        updateText();
    }
}