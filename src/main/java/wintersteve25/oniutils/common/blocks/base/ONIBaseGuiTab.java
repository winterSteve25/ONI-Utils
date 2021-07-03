package wintersteve25.oniutils.common.blocks.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.oniutils.ONIUtils;

@OnlyIn(Dist.CLIENT)
public abstract class ONIBaseGuiTab extends Screen {

    private static ResourceLocation bg = null;
    private static int widt = 0;
    private static int heigh = 0;

    protected ONIBaseGuiTab(ITextComponent titleIn, ResourceLocation resourceLocation, int wid, int hei) {
        super(titleIn);

        bg = resourceLocation;
        widt = wid;
        heigh = hei;
    }

    @Override
    protected void init() {
        int relX = (this.width - widt) / 2;
        int relY = (this.height - heigh) / 2;

//        addButton(new ONIBaseButton(relX + 155, relY + 6, 8, 8, new TranslationTextComponent(""), button -> back(), new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/back_button")));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (bg != null && widt != 0 && heigh != 0) {
            RenderSystem.color4f(1F, 1F, 1F, 1F);
            this.minecraft.getTextureManager().bindTexture(bg);

            int i = (this.width - widt) / 2;
            int j = (this.height - heigh) / 2;

            this.blit(matrixStack, i, j, 0, 0, widt, heigh);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            this.renderBackground(matrixStack);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
