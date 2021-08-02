package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;

import java.util.Locale;

public class ONIBaseGuiTabRedstone extends ONIBaseGuiTab implements IGuiEventListener {
    public static final TranslationTextComponent LOW = new TranslationTextComponent(REDSTONE_LOW);
    public static final TranslationTextComponent HIGH = new TranslationTextComponent(REDSTONE_HIGH);
    public static final TranslationTextComponent INVALID_NUMBER = new TranslationTextComponent(REDSTONE_INVALID_NUMBER);

    private TextFieldWidget lowText;
    private TextFieldWidget highText;
    private int lastInputLow = 0;
    private int lastInputHigh = 0;
    private boolean renderError = false;

    @Override
    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, String title) {
        super.init(widthIn, heightIn, minecraftIn, containerIn, title);
        this.mc.keyboardListener.enableRepeatEvents(true);
        initTextBars();
    }

    public void removed() {
        this.lowText = null;
        this.highText = null;
        this.mc.keyboardListener.enableRepeatEvents(false);
    }

    public void initTextBars() {
        int i = getGuiLeftTopPosition(this.width, 177) - 147;
        String low = this.lowText != null ? this.lowText.getText() : "";
        String high = this.highText != null ? this.highText.getText() : "";
        this.lowText = new TextFieldWidget(this.mc.fontRenderer, i + 10, 200, 80, 9 + 5, new TranslationTextComponent("Hi"));
        this.lowText.setMaxStringLength(50);
        this.lowText.setEnableBackgroundDrawing(true);
        this.lowText.setVisible(true);
        this.lowText.setTextColor(16777215);
        this.lowText.setText(low);

        this.highText = new TextFieldWidget(this.mc.fontRenderer, i + 10, 240, 80, 9 + 5, HIGH);
        this.highText.setMaxStringLength(50);
        this.highText.setEnableBackgroundDrawing(true);
        this.highText.setVisible(true);
        this.highText.setTextColor(16777215);
        this.highText.setText(high);

        updateLastInput();
    }

    public void tick() {
        this.lowText.tick();
        this.highText.tick();
    }

    private void updateLastInput() {
        String first = this.lowText.getText().toLowerCase(Locale.ROOT);
        try {
            int firstInt = Integer.parseInt(first);
            if (firstInt != (this.lastInputLow)) {
                lastInputLow = firstInt;
            }
        } catch (NumberFormatException e) {
            renderError = true;
        }

        String second = this.highText.getText().toLowerCase(Locale.ROOT);
        try {
            int secondInt = Integer.parseInt(second);
            if (secondInt != (this.lastInputHigh)) {
                lastInputHigh = secondInt;
            }
        } catch (NumberFormatException e) {
            renderError = true;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);

            int j = (this.height - 167) / 2;

            if (!this.lowText.isFocused() && this.lowText.getText().isEmpty()) {
                mc.fontRenderer.func_243246_a(matrixStack, LOW, getGuiLeftTopPosition(this.width, 177) - 147 + 10, j+25, TextFormatting.GRAY.getColor());
            } else {
                this.lowText.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            if (!this.lowText.isFocused() && this.lowText.getText().isEmpty()) {
                mc.fontRenderer.func_243246_a(matrixStack, HIGH, getGuiLeftTopPosition(this.width, 177) - 147 + 10, j+40, TextFormatting.GRAY.getColor());
            } else {
                this.highText.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            updateLastInput();

//            if (renderError) {
//                int ticks = 60;
//                ticks--;
//                if (ticks <= 0) {
//                    renderError = false;
//                }
//                mc.fontRenderer.func_243246_a(matrixStack, INVALID_NUMBER, (getGuiLeftTopPosition(this.width, 177) - 147)+10, 100, TextFormatting.RED.getColor());
//            }

            RenderSystem.popMatrix();
        }
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.lowText.mouseClicked(mouseX, mouseY, button) || this.highText.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return IGuiEventListener.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.lowText.keyPressed(keyCode, scanCode, modifiers) || this.highText.keyPressed(keyCode, scanCode, modifiers)) {
                updateLastInput();
                return true;
            } else if ((this.lowText.isFocused() && this.lowText.getVisible()) && keyCode != 256 || (this.highText.isFocused() && this.highText.getVisible())) {
                return true;
            } else if (this.mc.gameSettings.keyBindChat.matchesKey(keyCode, scanCode) && !this.lowText.isFocused() && !this.highText.isFocused()) {
                this.lowText.setFocused2(true);
                this.highText.setFocused2(true);
                return true;
            } else {
                return false;
            }
        }
        return IGuiEventListener.super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.isVisible() && !this.mc.player.isSpectator()) {
            if (this.lowText.charTyped(codePoint, modifiers) || this.highText.charTyped(codePoint, modifiers)) {
                updateLastInput();
                return true;
            } else {
                return IGuiEventListener.super.charTyped(codePoint, modifiers);
            }
        }
        return false;
    }
}
