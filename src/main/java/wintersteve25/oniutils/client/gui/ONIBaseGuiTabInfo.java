package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class ONIBaseGuiTabInfo extends ONIBaseGuiTab {

    protected TranslatableComponent ENERGY;
    protected TranslatableComponent PRODUCING_ENERGY;
    protected TranslatableComponent CONSUMING_ENERGY;
    protected TranslatableComponent PROGRESS;
    protected TranslatableComponent PRODUCING_GAS;
    protected TranslatableComponent CONSUMING_GAS;
    protected TranslatableComponent PRODUCING_LIQUID;
    protected TranslatableComponent CONSUMING_LIQUID;

    protected List<TranslatableComponent> initializedTexts = new ArrayList<>();

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 100);
            renderTexts(matrixStack);
            matrixStack.popPose();
        }
    }

    public void updateText() {
        initializedTexts.clear();

        if (container.hasProgress()) {
            PROGRESS = new TranslatableComponent(ONIBaseGuiTab.PROGRESS, container.getProgress()/10);
            initializedTexts.add(PROGRESS);
        }
        if (container.hasPower()) {
            ENERGY = new TranslatableComponent(ONIBaseGuiTab.ENERGY, container.getPower());
            initializedTexts.add(ENERGY);
            if (container.isPowerProducer()) {
                PRODUCING_ENERGY = new TranslatableComponent(ONIBaseGuiTab.PRODUCING_ENERGY, container.getProducingPower());
                initializedTexts.add(PRODUCING_ENERGY);
            }
            if (container.isPowerConsumer()) {
                CONSUMING_ENERGY = new TranslatableComponent(ONIBaseGuiTab.CONSUMING_ENERGY, container.getConsumingPower());
                initializedTexts.add(CONSUMING_ENERGY);
            }
        }
        if (container.isGasProducer()) {
            PRODUCING_GAS = new TranslatableComponent(ONIBaseGuiTab.PRODUCING_GAS, container.getProducingGas());
            initializedTexts.add(PRODUCING_GAS);
        }
        if (container.isGasConsumer()) {
            CONSUMING_GAS = new TranslatableComponent(ONIBaseGuiTab.CONSUMING_GAS, container.getConsumingGas());
            initializedTexts.add(CONSUMING_GAS);
        }
        if (container.isLiquidProducer()) {
            PRODUCING_LIQUID = new TranslatableComponent(ONIBaseGuiTab.PRODUCING_LIQUID, container.getProducingLiquid());
            initializedTexts.add(PRODUCING_LIQUID);
        }
        if (container.isLiquidConsumer()) {
            CONSUMING_LIQUID = new TranslatableComponent(ONIBaseGuiTab.CONSUMING_LIQUID, container.getConsumingLiquid());
            initializedTexts.add(CONSUMING_LIQUID);
        }
    }

    public void renderTexts(PoseStack mx) {
        int ogPos = ((this.height - 167) / 2) + 26;
        int toAddPos = 12;

        for (int i = 0; i < initializedTexts.size(); i++) {
            if (i == 0) {
                mc.font.drawShadow(mx, initializedTexts.get(i), (getGuiLeftTopPosition(this.width, 177) - 147) + 10, ogPos, ChatFormatting.GRAY.getColor());
            } else {
                mc.font.drawShadow(mx, initializedTexts.get(i), (getGuiLeftTopPosition(this.width, 177) - 147)+10, ogPos+toAddPos*i, ChatFormatting.GRAY.getColor());
            }
        }

        updateText();
    }
}