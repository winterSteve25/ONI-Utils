package wintersteve25.oniutils.common.blocks.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ONIBaseButton extends Button {
    private static ResourceLocation ICON = null;

    public ONIBaseButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ResourceLocation RL) {
        super(x, y, width, height, title, pressedAction);
        ICON = RL;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        superRenderButton(matrixStack, mouseX, mouseY);
    }

    public void superRenderButton(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (ICON != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bindTexture(ICON);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            int i = this.isHovered() ? height : 0;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            this.blit(matrixStack, this.x, this.y, 0, i, this.width, this.height);
            this.renderBg(matrixStack, minecraft, mouseX, mouseY);
        }
    }
}
