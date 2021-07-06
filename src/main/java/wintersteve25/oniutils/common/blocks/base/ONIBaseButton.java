package wintersteve25.oniutils.common.blocks.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ONIBaseButton extends Button {
    private ResourceLocation RL;

    public ONIBaseButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ResourceLocation resourceLocation) {
        super(x, y, width, height, title, pressedAction);

        RL = resourceLocation;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);

        if (RL != null) {
            Minecraft.getInstance().getTextureManager().bindTexture(RL);
            this.blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height);
        }
    }
}
