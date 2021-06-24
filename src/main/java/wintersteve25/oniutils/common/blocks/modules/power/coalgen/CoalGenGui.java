package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseGui;

@OnlyIn(Dist.CLIENT)
public class CoalGenGui extends ONIBaseGui<CoalGenContainer> {

    private static ResourceLocation bg = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/coal_gen_gui.png");

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name, bg);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (bg != null) {
            super.renderBg(matrixStack, partialTicks, mouseX, mouseY);

            int progress = menu.getProgress();
            int power = menu.getEnergy();

//            int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
//            int percentProgress = 100 - ((totalProgress-progress)/totalProgress*100);

            int i = (this.width - this.getXSize()) / 2;
            int j = (this.height - this.getYSize()) / 2;

            this.blit(matrixStack, i + 106, j + 25 + (int) ((4000 - power) / 83.3), 177, (int) ((4000 - power) / 83.3), 16, (int) (48 - ((4000 - power) / 83.3) + 1));

            if (menu.getWorking() == 1) {
                //burn
                this.blit(matrixStack, i+56, j+51, 194, 62, 14, progress+1);

                //progress
                this.blit(matrixStack, i+78, j+36, 208, 1, progress+1, 10);
            }
        }
    }


}
