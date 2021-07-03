package wintersteve25.oniutils.common.blocks.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.oniutils.ONIUtils;

@OnlyIn(Dist.CLIENT)
public class ONIBaseGuiTabInfo extends ONIBaseGuiTab {
    public ONIBaseGuiTabInfo(ITextComponent titleIn) {
        super(titleIn, new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/info_tab.png"), 175, 88);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        FontRenderer fontrenderer = minecraft.fontRenderer;
        drawCenteredString(matrixStack, fontrenderer, "test", 4, 40, 40);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
