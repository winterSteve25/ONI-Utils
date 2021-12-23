package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ONIBaseGuiTabAlert extends ONIBaseGuiTab {

    public static final TranslationTextComponent WARNING_DURABILITY = new TranslationTextComponent(ONIBaseGuiTab.WARNING_DURABILITY);
    public static final TranslationTextComponent WARNING_TEMPERATURE = new TranslationTextComponent(ONIBaseGuiTab.WARNING_TEMPERATURE);
    public static final TranslationTextComponent WARNING_GAS_PRESSURE = new TranslationTextComponent(ONIBaseGuiTab.WARNING_GAS_PRESSURE);
    public static final TranslationTextComponent WARNING_WRONG_GAS = new TranslationTextComponent(ONIBaseGuiTab.WARNING_WRONG_GAS);
    public static final TranslationTextComponent WARNING_ALL_CLEAR = new TranslationTextComponent(ONIBaseGuiTab.WARNING_ALL_CLEAR);

    private final List<TranslationTextComponent> currentWarnings = new ArrayList<>();

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            renderWarnings(currentWarnings.size(), matrixStack);
            RenderSystem.popMatrix();
        }
    }

    public void addWarning(TranslationTextComponent add) {
        currentWarnings.add(add);
    }

    public void removeWarning(TranslationTextComponent remove) {
        currentWarnings.remove(remove);
    }

    //ugly hack but it works :/
    public void renderWarnings(int warningCount, MatrixStack mx) {
        int ogPos = ((this.height - 167) / 2) + 26;
        int toAddPos = 12;

        switch (warningCount) {
            case 0:
                mc.fontRenderer.func_243246_a(mx, WARNING_ALL_CLEAR, (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos, TextFormatting.GRAY.getColor());
                break;
            case 1:
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos, TextFormatting.RED.getColor());
                break;
            case 2:
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos, TextFormatting.RED.getColor());
                break;
            case 3:
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(2), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos * 2, TextFormatting.RED.getColor());
                break;
            case 4:
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(0), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(1), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(2), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos * 2, TextFormatting.RED.getColor());
                mc.fontRenderer.func_243246_a(mx, currentWarnings.get(3), (getGuiLeftTopPosition(this.width, 177)) + 10, ogPos + toAddPos * 3, TextFormatting.RED.getColor());
                break;
        }
    }
}
