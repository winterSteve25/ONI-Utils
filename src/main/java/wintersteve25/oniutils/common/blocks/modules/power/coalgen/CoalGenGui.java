package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseButtonContainerSide;
import wintersteve25.oniutils.common.blocks.base.ONIBaseGuiContainer;
import wintersteve25.oniutils.common.blocks.base.ONIBaseGuiTabInfo;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class CoalGenGui extends ONIBaseGuiContainer<CoalGenContainer> {

    private static ResourceLocation bg = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/coal_gen_gui.png");

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name, bg);
    }

    @Override
    protected void init() {
        super.init();

        int i = (this.width - this.getXSize()) / 2;
        int j = (this.height - this.getYSize()) / 2;

        addButton(new ONIBaseButtonContainerSide.EnergyButton(i-16, j+4, button -> openInfoScreen()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (bg != null) {
            super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);

            int progress = container.getProgress();
            int power = container.getEnergy();

            int i = (this.width - this.getXSize()) / 2;
            int j = (this.height - this.getYSize()) / 2;

//            int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
//            int percentProgress = 100 - ((totalProgress-progress)/totalProgress*100);

            this.blit(matrixStack, i + 106, j + 25 + (int) ((4000 - power) / 83.3), 177, (int) ((4000 - power) / 83.3), 16, (int) (48 - ((4000 - power) / 83.3) + 1));

            if (container.getWorking() == 1) {
                //burn
                this.blit(matrixStack, i+56, j+51, 194, 62, 14, progress+1);

                //progress
                this.blit(matrixStack, i+78, j+36, 208, 1, progress+1, 10);
            }

            super.powerToolTip(matrixStack, power, mouseX, mouseY, i, j);
        }
    }

    private void openInfoScreen() {
        minecraft.displayGuiScreen(null);
        minecraft.displayGuiScreen(new ONIBaseGuiTabInfo(new TranslationTextComponent("")));
    }
}
