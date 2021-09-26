package wintersteve25.oniutils.common.blocks.modules.power.coal;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;
import wintersteve25.oniutils.client.utils.RenderingHelper;
import wintersteve25.oniutils.common.utils.ONIConstants;

import javax.annotation.Nonnull;

public class CoalGenGui extends ONIBaseGuiContainer<CoalGenContainer> {

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (bg != null) {
            super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);

            int power = container.getEnergy();

            RenderingHelper.bindWidgetsTexture();
            RenderingHelper.renderWidget(this, ONIConstants.Resources.ITEM_SLOT, matrixStack, this.guiLeft + 54, this.guiTop + 31);
            RenderingHelper.renderWidget(this, ONIConstants.Resources.RIGHT_ARROW_BIG_BG, matrixStack, this.guiLeft + 79, this.guiTop + 36);
            RenderingHelper.renderWidget(this, ONIConstants.Resources.FLAME_BG, matrixStack, this.guiLeft + 56, this.guiTop + 51);
            RenderingHelper.renderWidget(this, ONIConstants.Resources.POWER_BAR_BG, matrixStack, this.guiLeft + 105, this.guiTop + 24);
            RenderingHelper.renderWidget(this, ONIConstants.Resources.BATTERY_BG, matrixStack, this.guiLeft + 105, this.guiTop + 6);

            this.blit(matrixStack, this.guiLeft + 106, this.guiTop + 25 + (int) ((container.getCapacity() - power) / 83.3), ONIConstants.Resources.POWER_BAR.getU(), ONIConstants.Resources.POWER_BAR.getV() + (int) ((container.getCapacity() - power) / 83.3), 16, (int) (48 - ((container.getCapacity() - power) / 83.3) + 1));

            if (container.getWorking() == 1) {
                int p = getProgressScaledHorizontal(22);
                int f = getProgressScaledVertical(13);

                //burn
                RenderingHelper.renderAnimatedFlame(this, matrixStack, this.guiLeft + 56, this.guiTop + 51 + f, f);

                //progress
                RenderingHelper.renderAnimatedProgressBar(this, matrixStack, this.guiLeft + 78, this.guiTop + 36, p);
            }

            super.powerToolTip(matrixStack, power, mouseX, mouseY, this.guiLeft, this.guiTop);
        }
    }

    @Override
    protected boolean hasRedstoneOutputButton() {
        return true;
    }
}
