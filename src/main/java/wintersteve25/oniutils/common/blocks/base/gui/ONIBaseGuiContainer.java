package wintersteve25.oniutils.common.blocks.base.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;

public class ONIBaseGuiContainer<T extends ONIBaseContainer> extends ContainerScreen<T> {

    private static ResourceLocation bg = null;


    public ONIBaseGuiContainer(T container, PlayerInventory inv, ITextComponent name, ResourceLocation resourceLocation) {
        super(container, inv, name);

        bg = resourceLocation;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.getXSize()) / 2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (bg != null) {
            RenderSystem.color4f(1F, 1F, 1F, 1F);
            this.minecraft.getTextureManager().bindTexture(bg);

            int j = (this.height - this.getYSize()) / 2;

            this.blit(matrixStack, this.guiLeft, j, 0, 0, this.getXSize(), this.getYSize() + 5);
        }
    }

    protected void powerToolTip(MatrixStack matrixStack, int power, int mouseX, int mouseY, int i, int j) {
        if (mouseX > i + 105 && mouseY > j + 24 && mouseX < i + 122 && mouseY < j + 73) {
            renderTooltip(matrixStack, new TranslationTextComponent("oniutils.gui.machines.power", power), mouseX, mouseY);
        }
    }

    protected void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY, int i, int j, int cord1, int cord1Y, int cord2, int cord2Y, ITextComponent text) {
        if (mouseX > i + cord1 && mouseY > j + cord1Y && mouseX < i + cord2 && mouseY < j + cord2Y) {
            renderTooltip(matrixStack, text, mouseX, mouseY);
        }
    }

    protected int getProgressScaled(int pixels) {
        int i = container.getProgress();
        int j = container.getTotalProgress();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
