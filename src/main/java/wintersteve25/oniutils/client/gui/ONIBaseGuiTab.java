package wintersteve25.oniutils.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;

public class ONIBaseGuiTab extends Screen implements NarratableEntry {

    protected static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/tab_base.png");

    public static final String ENERGY = "oniutils.gui.info.energy";
    public static final String PRODUCING_ENERGY = "oniutils.gui.info.producingEnergy";
    public static final String CONSUMING_ENERGY = "oniutils.gui.info.consumingEnergy";
    public static final String PROGRESS = "oniutils.gui.info.progress";
    public static final String PRODUCING_GAS = "oniutils.gui.info.producingGas";
    public static final String CONSUMING_GAS = "oniutils.gui.info.consumingGas";
    public static final String PRODUCING_LIQUID = "oniutils.gui.info.producingLiquid";
    public static final String CONSUMING_LIQUID = "oniutils.gui.info.consumingLiquid";

    public static final String WARNING_DURABILITY = "oniutils.gui.warning.durability";
    public static final String WARNING_TEMPERATURE = "oniutils.gui.warning.temperature";
    public static final String WARNING_GAS_PRESSURE = "oniutils.gui.warning.gasPressure";
    public static final String WARNING_WRONG_GAS = "oniutils.gui.warning.wrongGas";
    public static final String WARNING_ALL_CLEAR = "oniutils.gui.warning.allClear";

    public static final String REDSTONE_LOW = "oniutils.gui.redstone.low";
    public static final String REDSTONE_HIGH = "oniutils.gui.redstone.high";
    public static final String REDSTONE_INVALID_NUMBER = "oniutils.gui.redstone.invalid_number";

    protected Component title;

    protected int width;
    protected int height;
    protected boolean isVisible;

    protected Minecraft mc;
    protected ONIBaseContainer container;

    protected ONIBaseGuiTab() {
        super(new TranslatableComponent(""));
    }

    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, Component title) {
        this.width = widthIn;
        this.height = heightIn;
        this.mc = minecraftIn;
        this.container = containerIn;
        this.title = title;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void toggleVisibility() {
        this.isVisible = !isVisible();
    }

    public int getGuiLeftTopPosition(int width, int xSize) {
        if (this.isVisible()) {
            return 177 + (width - xSize - 200) / 2;
        }

        return (width - xSize) / 2;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 0);
            RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
            int i = getGuiLeftTopPosition(this.width, 177) - 147;
            int j = (this.height - 167) / 2;
            this.blit(matrixStack, i, j, 1, 1, 147, 170);
            mc.font.drawShadow(matrixStack, title, i + 10, j + 10, ChatFormatting.WHITE.getColor());
            matrixStack.popPose();
        }
    }

    public int getY() {
        return (this.height - 167) / 2;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}