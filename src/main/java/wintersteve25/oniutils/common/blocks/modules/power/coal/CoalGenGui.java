package wintersteve25.oniutils.common.blocks.modules.power.coal;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;

import javax.annotation.Nonnull;

public class CoalGenGui extends ONIBaseGuiContainer<CoalGenContainer> {

    private static ResourceLocation bg = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/coal_gen_gui.png");

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name, bg);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (bg != null) {
            super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);

            int power = container.getEnergy();

            this.blit(matrixStack, this.guiLeft +  106, this.guiTop +  25 + (int) ((container.getCapacity() - power) / 83.3), 177, (int) ((container.getCapacity() - power) / 83.3), 16, (int) (48 - ((container.getCapacity() - power) / 83.3) + 1));

            if (container.getWorking() == 1) {
                int p = getProgressScaled(22);
                int f = getProgressScaled(13);

                //burn
                this.blit(matrixStack, this.guiLeft + 56, this.guiTop + 51, 197, 1, 14, f+1);

                //progress
                this.blit(matrixStack, this.guiLeft + 78, this.guiTop + 36, 177, 51, p+1, 10);
            }

            super.powerToolTip(matrixStack, power, mouseX, mouseY, this.guiLeft, this.guiTop);
        }
    }

    @Override
    protected boolean hasRedstoneOutputButton() {
        return true;
    }
}
