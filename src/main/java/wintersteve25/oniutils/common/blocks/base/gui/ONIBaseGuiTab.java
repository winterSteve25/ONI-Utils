package wintersteve25.oniutils.common.blocks.base.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;

public class ONIBaseGuiTab extends AbstractGui implements IRenderable {

    protected static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/tab_base.png");

    protected static final String ENERGY = "oniutils.gui.info.energy";
    protected static final String PRODUCING_ENERGY = "oniutils.gui.info.producingEnergy";
    protected static final String CONSUMING_ENERGY = "oniutils.gui.info.consumingEnergy";
    protected static final String PROGRESS = "oniutils.gui.info.progress";
    protected static final String PRODUCING_GAS = "oniutils.gui.info.producingGas";
    protected static final String CONSUMING_GAS = "oniutils.gui.info.consumingGas";
    protected static final String PRODUCING_LIQUID = "oniutils.gui.info.producingLiquid";
    protected static final String CONSUMING_LIQUID = "oniutils.gui.info.consumingLiquid";

    protected static final String WARNING_DURABILITY = "oniutils.gui.warning.durability";
    protected static final String WARNING_TEMPERATURE = "oniutils.gui.warning.temperature";
    protected static final String WARNING_GAS_PRESSURE = "oniutils.gui.warning.gasPressure";
    protected static final String WARNING_WRONG_GAS = "oniutils.gui.warning.wrongGas";
    protected static final String WARNING_ALL_CLEAR = "oniutils.gui.warning.allClear";

    private TranslationTextComponent title;

    protected int width;
    protected int height;
    protected boolean isVisible;

    protected Minecraft mc;
    protected ONIBaseContainer container;

    public void init(int widthIn, int heightIn, Minecraft minecraftIn, ONIBaseContainer containerIn, String title) {
        this.width = widthIn;
        this.height = heightIn;
        this.mc = minecraftIn;
        this.container = containerIn;
        this.title = new TranslationTextComponent(title);
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible()) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            this.mc.getTextureManager().bindTexture(BACKGROUND_LOCATION);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = getGuiLeftTopPosition(this.width, 177) - 147;
            int j = (this.height - 167) / 2;
            this.blit(matrixStack, i, j, 1, 1, 147, 170);
            mc.fontRenderer.func_243246_a(matrixStack, title, i + 10, j + 10, TextFormatting.WHITE.getColor());
            RenderSystem.popMatrix();
        }
    }
}
