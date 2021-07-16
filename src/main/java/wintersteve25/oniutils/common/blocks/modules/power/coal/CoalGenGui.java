package wintersteve25.oniutils.common.blocks.modules.power.coal;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.gui.ONIBaseGuiContainer;
import wintersteve25.oniutils.common.blocks.base.gui.ONIBaseGuiTab;
import wintersteve25.oniutils.common.blocks.base.gui.ONIBaseGuiTabInfo;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class CoalGenGui extends ONIBaseGuiContainer<CoalGenContainer> {

    private static ResourceLocation bg = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/coal_gen_gui.png");
    public final ONIBaseGuiTabInfo infoTab;

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name, bg);

        infoTab = new ONIBaseGuiTabInfo();
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = this.infoTab.getGuiLeftTopPosition(this.width, this.xSize);
        this.guiTop = (this.height - this.getYSize()) / 2;

        this.infoTab.init(this.width, this.height, this.minecraft, this.container, "oniutils.gui.titles.coal_gen");
        this.infoTab.initText();

        addButton(new InfoButton());
        addButton(new AlertButton());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        infoTab.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (bg != null) {
            super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);

            int power = container.getEnergy();

//            int totalProgress = ONIConfig.COAL_GEN_PROCESS_TIME.get();
//            int percentProgress = 100 - ((totalProgress-progress)/totalProgress*100);

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

    //TODO: Use Vanilla ImageButton instead
    @OnlyIn(Dist.CLIENT)
    abstract static class Button extends AbstractButton {
        protected Button(int x, int y) {
            super(x, y, 16, 16, StringTextComponent.EMPTY);
        }

        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(CoalGenGui.bg);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            int j = 0;

            if (this.isHovered()) {
                j += this.width;
            }

            this.blit(matrixStack, this.x, this.y, j, 240, this.width, this.height);
            this.blitOverlay(matrixStack);
        }

        public void setPosition(int xIn, int yIn) {
            this.x = xIn;
            this.y = yIn;
        }

        protected abstract void blitOverlay(MatrixStack matrixStack);
    }

    @OnlyIn(Dist.CLIENT)
    class InfoButton extends CoalGenGui.SpriteButton {
        public InfoButton() {
            super(CoalGenGui.this.guiLeft+1, CoalGenGui.this.guiTop-17, 32, 240);
        }

        public void onPress() {
            CoalGenGui.this.infoTab.toggleVisibility();
            CoalGenGui.this.guiLeft = CoalGenGui.this.infoTab.getGuiLeftTopPosition(CoalGenGui.this.width, CoalGenGui.this.xSize);

            this.setPosition(CoalGenGui.this.guiLeft+1, CoalGenGui.this.guiTop-17);
        }
    }

    @OnlyIn(Dist.CLIENT)
    class AlertButton extends CoalGenGui.SpriteButton {
        public AlertButton() {
            super(CoalGenGui.this.guiLeft+18, CoalGenGui.this.guiTop-17, 49, 240);
        }

        public void onPress() {
//            CoalGenGui.this.guiLeft = CoalGenGui.this.infoTab.getGuiLeftTopPosition(CoalGenGui.this.width, CoalGenGui.this.xSize);

//            this.setPosition(CoalGenGui.this.guiLeft+1, CoalGenGui.this.guiTop-17);
        }
    }

    @OnlyIn(Dist.CLIENT)
    abstract static class SpriteButton extends CoalGenGui.Button {
        protected int u;
        protected int v;

        protected SpriteButton(int x, int y, int u, int v) {
            super(x, y);

            this.u = u;
            this.v = v;
        }

        protected void setU(int u) {
            this.u = u;
        }

        protected void setV(int v) {
            this.v = v;
        }

        protected void blitOverlay(MatrixStack matrixStack) {
            this.blit(matrixStack, this.x, this.y, this.u, this.v, 16, 16);
        }
    }
}
