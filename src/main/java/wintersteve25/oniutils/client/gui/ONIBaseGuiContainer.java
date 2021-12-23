package wintersteve25.oniutils.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.client.gui.GuiUtils;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import wintersteve25.oniutils.api.ONIIModifiable;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TEPosBasedPacket;
import wintersteve25.oniutils.common.utils.ONIConstants;

public abstract class ONIBaseGuiContainer<T extends ONIBaseContainer> extends ContainerScreen<T> {

    public static ResourceLocation bg = null;

    protected ONIBaseGuiTabInfo infoTab;
    protected final ONIBaseGuiTabAlert alertTab;
    protected ONIBaseGuiTabRedstone redstoneOutputTab;
    protected ONIBaseGuiTabModification modificationTab;
    protected InfoButton infoButton;
    protected AlertButton alertButton;
    protected RedstoneOutputButton redstoneOutputButton;
    protected ModificationButton modificationButton;
    protected RedstoneButton redstoneButton;
    public ONIBaseGuiTab currentTab;

    public ONIBaseGuiContainer(T container, PlayerInventory inv, ITextComponent name, ResourceLocation resourceLocation) {
        super(container, inv, name);

        bg = resourceLocation;
        infoTab = new ONIBaseGuiTabInfo();
        alertTab = new ONIBaseGuiTabAlert();
        currentTab = infoTab;

        if (hasRedstoneOutputButton()) {
            redstoneOutputTab = new ONIBaseGuiTabRedstone();
        }
        if (hasModButton()) {
            modificationTab = new ONIBaseGuiTabModification();
        }
    }

    public ONIBaseGuiContainer(T container, PlayerInventory inv, ITextComponent name) {
        this(container, inv, name, ONIConstants.Resources.BLANK_GUI);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = this.currentTab.getGuiLeftTopPosition(this.width, this.xSize);
        this.guiTop = (this.height - this.getYSize()) / 2;

        this.infoTab.init(this.width, this.height, this.minecraft, this.container, this.container.getTileEntity().machineName());
        this.infoTab.updateText();

        this.alertTab.init(this.width, this.height, this.minecraft, this.container, new TranslationTextComponent("oniutils.gui.titles.warning"));

        if (hasRedstoneOutputButton()) {
            redstoneOutputTab.init(this.width, this.height, this.minecraft, this.container, new TranslationTextComponent("oniutils.gui.titles.redstoneOutput"));
            this.redstoneOutputButton = new RedstoneOutputButton();
            this.children.add(redstoneOutputTab);
            addButton(redstoneOutputButton);
        }

        if (hasModButton()) {
            modificationTab.init(this.width, this.height, this.minecraft, this.container, new TranslationTextComponent("oniutils.gui.titles.modifications"));
            this.modificationButton = new ModificationButton();
            addButton(modificationButton);
        }

        this.infoButton = new InfoButton();
        this.alertButton = new AlertButton();
        this.redstoneButton = new RedstoneButton();

        addButton(infoButton);
        addListener(alertTab);
        addButton(alertButton);
        addButton(redstoneButton);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

        if (currentTab != null) {
            currentTab.render(matrixStack, mouseX, mouseY, partialTicks);
        }
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

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        if (shouldKeepInventoryText()) {
            super.drawGuiContainerForegroundLayer(matrixStack, x, y);
        } else {
            this.font.func_243248_b(matrixStack, this.title, (float) this.titleX, (float) this.titleY, 4210752);
        }
    }

    protected void powerToolTip(MatrixStack matrixStack, int power, int mouseX, int mouseY, int i, int j) {
        if (mouseX > i + 105 && mouseY > j + 24 && mouseX < i + 122 && mouseY < j + 73) {
            renderTooltip(matrixStack, new TranslationTextComponent("oniutils.gui.machines.power", power), mouseX, mouseY);
        }
    }

    protected void renderCustomToolTip(MatrixStack matrixStack, int mouseX, int mouseY, int i, int j, int cord1, int cord1Y, int cord2, int cord2Y, ITextComponent text) {
        if (mouseX > i + cord1 && mouseY > j + cord1Y && mouseX < i + cord2 && mouseY < j + cord2Y) {
            renderTooltip(matrixStack, text, mouseX, mouseY);
        }
    }

    protected int getScaledProgress(float pixels) {
        float totalProgress = container.getTotalProgress();
        float progress = totalProgress - container.getProgress();

        if (totalProgress != 0) {
            float result = progress*pixels/totalProgress;
            return Math.round(result);
        }

        return 0;
    }

    protected boolean shouldKeepInventoryText() {
        return false;
    }

    public boolean isTabOpen() {
        return currentTab.isVisible();
    }

    abstract class Button extends AbstractButton {
        private final ITextProperties name;

        protected Button(int x, int y, ITextProperties name) {
            super(x, y, 16, 16, StringTextComponent.EMPTY);
            this.name = name;
        }

        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindTexture(ONIConstants.Resources.WIDGETS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            int j = 0;

            if (this.isHovered()) {
                j += this.width;
            }

            this.blit(matrixStack, this.x, this.y, j, 0, this.width, this.height);
            this.blitOverlay(matrixStack);

            if (this.isHovered()) {
                GuiUtils.drawHoveringText(matrixStack, Lists.newArrayList(name), mouseX, mouseY, ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.height, 200, ONIBaseGuiContainer.this.minecraft.fontRenderer);
            }
        }

        public void setPosition(int xIn, int yIn) {
            this.x = xIn;
            this.y = yIn;
        }

        protected abstract void blitOverlay(MatrixStack matrixStack);
    }

    protected class InfoButton extends SpriteButton {
        public InfoButton() {
            super(ONIBaseGuiContainer.this.guiLeft + 1, ONIBaseGuiContainer.this.guiTop - 17, ONIConstants.Resources.INFO_BUTTON.getU(), ONIConstants.Resources.INFO_BUTTON.getV(), ONIBaseGuiContainer.this.container.getTileEntity().machineName());
        }

        public void onPress() {
            if (ONIBaseGuiContainer.this.alertTab.isVisible()) {
                ONIBaseGuiContainer.this.alertTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                if (ONIBaseGuiContainer.this.redstoneOutputTab.isVisible()) {
                    ONIBaseGuiContainer.this.redstoneOutputTab.toggleVisibility();
                }
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                if (ONIBaseGuiContainer.this.modificationTab.isVisible) {
                    ONIBaseGuiContainer.this.modificationTab.toggleVisibility();
                }
            }


            ONIBaseGuiContainer.this.currentTab = infoTab;
            ONIBaseGuiContainer.this.infoTab.toggleVisibility();
            ONIBaseGuiContainer.this.guiLeft = ONIBaseGuiContainer.this.infoTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.xSize);

            this.setPosition(ONIBaseGuiContainer.this.guiLeft + 1, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 18, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 158, ONIBaseGuiContainer.this.guiTop - 17);

            //janky but works
            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 35, ONIBaseGuiContainer.this.guiTop - 17);
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 52, ONIBaseGuiContainer.this.guiTop - 17);
            }
        }
    }

    protected class AlertButton extends SpriteButton {
        public AlertButton() {
            super(ONIBaseGuiContainer.this.guiLeft + 18, ONIBaseGuiContainer.this.guiTop - 17, ONIConstants.Resources.ALERT_BUTTON.getU(), ONIConstants.Resources.ALERT_BUTTON.getV(), new TranslationTextComponent("oniutils.gui.titles.warning"));
        }

        public void onPress() {
            if (ONIBaseGuiContainer.this.infoTab.isVisible()) {
                ONIBaseGuiContainer.this.infoTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                if (ONIBaseGuiContainer.this.redstoneOutputTab.isVisible()) {
                    ONIBaseGuiContainer.this.redstoneOutputTab.toggleVisibility();
                }
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                if (ONIBaseGuiContainer.this.modificationTab.isVisible) {
                    ONIBaseGuiContainer.this.modificationTab.toggleVisibility();
                }
            }

            ONIBaseGuiContainer.this.currentTab = alertTab;
            ONIBaseGuiContainer.this.alertTab.toggleVisibility();
            ONIBaseGuiContainer.this.guiLeft = ONIBaseGuiContainer.this.alertTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.xSize);

            this.setPosition(ONIBaseGuiContainer.this.guiLeft + 18, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 1, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 158, ONIBaseGuiContainer.this.guiTop - 17);

            //janky but works
            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 35, ONIBaseGuiContainer.this.guiTop - 17);
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 52, ONIBaseGuiContainer.this.guiTop - 17);
            }
        }
    }

    protected class RedstoneOutputButton extends SpriteButton {
        public RedstoneOutputButton() {
            super(ONIBaseGuiContainer.this.guiLeft + 35, ONIBaseGuiContainer.this.guiTop - 17, ONIConstants.Resources.REDSTONE_OUTPUT_BUTTON.getU(), ONIConstants.Resources.REDSTONE_OUTPUT_BUTTON.getV(), new TranslationTextComponent("oniutils.gui.titles.redstoneOutput"));
        }

        public void onPress() {
            if (ONIBaseGuiContainer.this.infoTab.isVisible()) {
                ONIBaseGuiContainer.this.infoTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.alertTab.isVisible()) {
                ONIBaseGuiContainer.this.alertTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                if (ONIBaseGuiContainer.this.modificationTab.isVisible) {
                    ONIBaseGuiContainer.this.modificationTab.toggleVisibility();
                }
            }

            ONIBaseGuiContainer.this.currentTab = redstoneOutputTab;
            ONIBaseGuiContainer.this.redstoneOutputTab.toggleVisibility();
            ONIBaseGuiContainer.this.guiLeft = ONIBaseGuiContainer.this.redstoneOutputTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.xSize);

            this.setPosition(ONIBaseGuiContainer.this.guiLeft + 35, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 1, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 18, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 158, ONIBaseGuiContainer.this.guiTop - 17);

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 52, ONIBaseGuiContainer.this.guiTop - 17);
            }
        }
    }

    protected class ModificationButton extends SpriteButton {
        public ModificationButton() {
            super(ONIBaseGuiContainer.this.guiLeft + 52, ONIBaseGuiContainer.this.guiTop - 17, ONIConstants.Resources.MODIFICATION_BUTTON.getU(), ONIConstants.Resources.MODIFICATION_BUTTON.getV(), new TranslationTextComponent("oniutils.gui.titles.modifications"));
        }

        public void onPress() {
            if (ONIBaseGuiContainer.this.infoTab.isVisible()) {
                ONIBaseGuiContainer.this.infoTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.alertTab.isVisible()) {
                ONIBaseGuiContainer.this.alertTab.toggleVisibility();
            }

            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                if (ONIBaseGuiContainer.this.redstoneOutputTab.isVisible()) {
                    ONIBaseGuiContainer.this.redstoneOutputTab.toggleVisibility();
                }
            }

            ONIBaseGuiContainer.this.currentTab = modificationTab;
            ONIBaseGuiContainer.this.modificationTab.toggleVisibility();
            ONIBaseGuiContainer.this.guiLeft = ONIBaseGuiContainer.this.modificationTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.xSize);

            this.setPosition(ONIBaseGuiContainer.this.guiLeft + 52, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 1, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 18, ONIBaseGuiContainer.this.guiTop - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 158, ONIBaseGuiContainer.this.guiTop - 17);

            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.guiLeft + 35, ONIBaseGuiContainer.this.guiTop - 17);
            }
        }
    }

    protected class RedstoneButton extends SpriteButton {
        public RedstoneButton() {
            super(ONIBaseGuiContainer.this.guiLeft + 158, ONIBaseGuiContainer.this.guiTop - 17, ONIConstants.Resources.REDSTONE_BUTTON_ON.getU(), ONIConstants.Resources.REDSTONE_BUTTON_ON.getV(), new TranslationTextComponent("oniutils.gui.titles.invert"));
            if (ONIBaseGuiContainer.this.container.getForceStopped() == 1) {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_OFF.getU());
            }
        }

        boolean pressed = ONIBaseGuiContainer.this.container.getForceStopped() == 1;

        public void onPress() {
            if (!pressed) {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_OFF.getU());
                pressed = true;
            } else {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_ON.getU());
                pressed = false;
            }
            ONINetworking.sendToServer(new TEPosBasedPacket(container.getTileEntity(), ONIConstants.PacketType.REDSTONE_INPUT));
        }
    }

    protected abstract class SpriteButton extends Button {
        protected int u;
        protected int v;

        protected SpriteButton(int x, int y, int u, int v, ITextProperties name) {
            super(x, y, name);

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

    protected boolean hasModButton() {
        return this.container.getTileEntity() instanceof ONIIModifiable;
    }

    protected abstract boolean hasRedstoneOutputButton();
}