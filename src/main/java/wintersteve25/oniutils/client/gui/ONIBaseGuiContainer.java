package wintersteve25.oniutils.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import wintersteve25.oniutils.api.ONIIModifiable;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TEPosBasedPacket;
import wintersteve25.oniutils.common.utils.ONIConstants;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class ONIBaseGuiContainer<T extends ONIBaseContainer> extends AbstractContainerScreen<T> {

    public final ResourceLocation bg;

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

    public ONIBaseGuiContainer(T container, Inventory inv, Component name, ResourceLocation resourceLocation) {
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

    public ONIBaseGuiContainer(T container, Inventory inv, Component name) {
        this(container, inv, name, ONIConstants.Resources.BLANK_GUI);
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = this.currentTab.getGuiLeftTopPosition(this.width, this.imageWidth);
        this.topPos = (this.height - this.getYSize()) / 2;

        this.infoTab.init(this.width, this.height, this.minecraft, this.menu, this.menu.getTileEntity().machineName());
        this.infoTab.updateText();

        this.alertTab.init(this.width, this.height, this.minecraft, this.menu, new TranslatableComponent("oniutils.gui.titles.warning"));

        if (hasRedstoneOutputButton()) {
            redstoneOutputTab.init(this.width, this.height, this.minecraft, this.menu, new TranslatableComponent("oniutils.gui.titles.redstoneOutput"));
            this.redstoneOutputButton = new RedstoneOutputButton();
            addWidget(redstoneOutputTab);
            addWidget(redstoneOutputButton);
        }

        if (hasModButton()) {
            modificationTab.init(this.width, this.height, this.minecraft, this.menu, new TranslatableComponent("oniutils.gui.titles.modifications"));
            this.modificationButton = new ModificationButton();
            addWidget(modificationButton);
        }

        this.infoButton = new InfoButton();
        this.alertButton = new AlertButton();
        this.redstoneButton = new RedstoneButton();

        addWidget(infoButton);
        addWidget(alertTab);
        addWidget(alertButton);
        addWidget(redstoneButton);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        if (currentTab != null) {
            currentTab.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        if (bg != null) {
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            this.minecraft.getTextureManager().bindForSetup(bg);
            int j = (this.height - this.getYSize()) / 2;
            this.blit(matrixStack, this.leftPos, j, 0, 0, this.getXSize(), this.getYSize() + 5);
        }
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        if (shouldKeepInventoryText()) {
            super.renderLabels(matrixStack, x, y);
        } else {
            this.font.draw(matrixStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 4210752);
        }
    }

    protected void powerToolTip(PoseStack matrixStack, int power, int mouseX, int mouseY, int i, int j) {
        if (mouseX > i + 105 && mouseY > j + 24 && mouseX < i + 122 && mouseY < j + 73) {
            renderTooltip(matrixStack, new TranslatableComponent("oniutils.gui.machines.power", power), mouseX, mouseY);
        }
    }

    protected void renderCustomToolTip(PoseStack matrixStack, int mouseX, int mouseY, int i, int j, int cord1, int cord1Y, int cord2, int cord2Y, Component text) {
        if (mouseX > i + cord1 && mouseY > j + cord1Y && mouseX < i + cord2 && mouseY < j + cord2Y) {
            renderTooltip(matrixStack, text, mouseX, mouseY);
        }
    }

    protected int getScaledProgress(float pixels) {
        float totalProgress = menu.getTotalProgress();
        float progress = totalProgress - menu.getProgress();

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
        private final Component name;

        protected Button(int x, int y, Component name) {
            super(x, y, 16, 16, TextComponent.EMPTY);
            this.name = name;
        }

        public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft.getInstance().getTextureManager().bindForSetup(ONIConstants.Resources.WIDGETS);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            int j = 0;

            if (this.isHovered) {
                j += this.width;
            }

            this.blit(matrixStack, this.x, this.y, j, 0, this.width, this.height);
            this.blitOverlay(matrixStack);

            if (this.isHovered) {
                renderTooltip(matrixStack, name, mouseX, mouseY);
            }
        }

        public void setPosition(int xIn, int yIn) {
            this.x = xIn;
            this.y = yIn;
        }

        @Override
        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
            pNarrationElementOutput.add(NarratedElementType.TITLE, name.getString());
        }

        protected abstract void blitOverlay(PoseStack matrixStack);
    }

    protected abstract class SpriteButton extends Button {
        protected int u;
        protected int v;

        protected SpriteButton(int x, int y, int u, int v, Component name) {
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

        protected void blitOverlay(PoseStack matrixStack) {
            this.blit(matrixStack, this.x, this.y, this.u, this.v, 16, 16);
        }
    }

    protected class InfoButton extends SpriteButton {
        public InfoButton() {
            super(ONIBaseGuiContainer.this.leftPos + 1, ONIBaseGuiContainer.this.topPos - 17, ONIConstants.Resources.INFO_BUTTON.getU(), ONIConstants.Resources.INFO_BUTTON.getV(), ONIBaseGuiContainer.this.menu.getTileEntity().machineName());
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
            ONIBaseGuiContainer.this.leftPos = ONIBaseGuiContainer.this.infoTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.imageWidth);

            this.setPosition(ONIBaseGuiContainer.this.leftPos + 1, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.leftPos + 18, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.leftPos + 158, ONIBaseGuiContainer.this.topPos - 17);

            //janky but works
            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.leftPos + 35, ONIBaseGuiContainer.this.topPos - 17);
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.leftPos + 52, ONIBaseGuiContainer.this.topPos - 17);
            }
        }
    }

    protected class AlertButton extends SpriteButton {
        public AlertButton() {
            super(ONIBaseGuiContainer.this.leftPos + 18, ONIBaseGuiContainer.this.topPos - 17, ONIConstants.Resources.ALERT_BUTTON.getU(), ONIConstants.Resources.ALERT_BUTTON.getV(), new TranslatableComponent("oniutils.gui.titles.warning"));
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
            ONIBaseGuiContainer.this.leftPos = ONIBaseGuiContainer.this.alertTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.imageWidth);

            this.setPosition(ONIBaseGuiContainer.this.leftPos + 18, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.leftPos + 1, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.leftPos + 158, ONIBaseGuiContainer.this.topPos - 17);

            //janky but works
            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.leftPos + 35, ONIBaseGuiContainer.this.topPos - 17);
            }

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.leftPos + 52, ONIBaseGuiContainer.this.topPos - 17);
            }
        }
    }

    protected class RedstoneOutputButton extends SpriteButton {
        public RedstoneOutputButton() {
            super(ONIBaseGuiContainer.this.leftPos + 35, ONIBaseGuiContainer.this.topPos - 17, ONIConstants.Resources.REDSTONE_OUTPUT_BUTTON.getU(), ONIConstants.Resources.REDSTONE_OUTPUT_BUTTON.getV(), new TranslatableComponent("oniutils.gui.titles.redstoneOutput"));
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
            ONIBaseGuiContainer.this.leftPos = ONIBaseGuiContainer.this.redstoneOutputTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.imageWidth);

            this.setPosition(ONIBaseGuiContainer.this.leftPos + 35, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.leftPos + 1, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.leftPos + 18, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.leftPos + 158, ONIBaseGuiContainer.this.topPos - 17);

            if (ONIBaseGuiContainer.this.hasModButton()) {
                ONIBaseGuiContainer.this.modificationButton.setPosition(ONIBaseGuiContainer.this.leftPos + 52, ONIBaseGuiContainer.this.topPos - 17);
            }
        }
    }

    protected class ModificationButton extends SpriteButton {
        public ModificationButton() {
            super(ONIBaseGuiContainer.this.leftPos + 52, ONIBaseGuiContainer.this.topPos - 17, ONIConstants.Resources.MODIFICATION_BUTTON.getU(), ONIConstants.Resources.MODIFICATION_BUTTON.getV(), new TranslatableComponent("oniutils.gui.titles.modifications"));
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
            ONIBaseGuiContainer.this.leftPos = ONIBaseGuiContainer.this.modificationTab.getGuiLeftTopPosition(ONIBaseGuiContainer.this.width, ONIBaseGuiContainer.this.imageWidth);

            this.setPosition(ONIBaseGuiContainer.this.leftPos + 52, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.infoButton.setPosition(ONIBaseGuiContainer.this.leftPos + 1, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.alertButton.setPosition(ONIBaseGuiContainer.this.leftPos + 18, ONIBaseGuiContainer.this.topPos - 17);
            ONIBaseGuiContainer.this.redstoneButton.setPosition(ONIBaseGuiContainer.this.leftPos + 158, ONIBaseGuiContainer.this.topPos - 17);

            if (ONIBaseGuiContainer.this.hasRedstoneOutputButton()) {
                ONIBaseGuiContainer.this.redstoneOutputButton.setPosition(ONIBaseGuiContainer.this.leftPos + 35, ONIBaseGuiContainer.this.topPos - 17);
            }
        }
    }

    protected class RedstoneButton extends SpriteButton {
        public RedstoneButton() {
            super(ONIBaseGuiContainer.this.leftPos + 158, ONIBaseGuiContainer.this.topPos - 17, ONIConstants.Resources.REDSTONE_BUTTON_ON.getU(), ONIConstants.Resources.REDSTONE_BUTTON_ON.getV(), new TranslatableComponent("oniutils.gui.titles.invert"));
            if (ONIBaseGuiContainer.this.menu.getForceStopped() == 1) {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_OFF.getU());
            }
        }

        boolean pressed = ONIBaseGuiContainer.this.menu.getForceStopped() == 1;

        public void onPress() {
            if (!pressed) {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_OFF.getU());
                pressed = true;
            } else {
                setU(ONIConstants.Resources.REDSTONE_BUTTON_ON.getU());
                pressed = false;
            }
            ONINetworking.sendToServer(new TEPosBasedPacket(menu.getTileEntity(), ONIConstants.PacketType.REDSTONE_INPUT));
        }
    }

    protected boolean hasModButton() {
        return this.menu.getTileEntity() instanceof ONIIModifiable;
    }

    protected abstract boolean hasRedstoneOutputButton();
}