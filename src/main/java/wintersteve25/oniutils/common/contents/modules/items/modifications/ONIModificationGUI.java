package wintersteve25.oniutils.common.contents.modules.items.modifications;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.ProgressOption;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.common.network.PacketModification;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.utils.ONIConstants;

public class ONIModificationGUI extends Screen {

    private final ItemStack modification;
    private AbstractWidget sliderWidget;

    private final int maxBonus;
    private static int bonus = 0;

    public ONIModificationGUI(ItemStack modification, int maxBonus) {
        super(new TranslatableComponent(""));
        this.modification = modification;
        this.maxBonus = maxBonus;
        bonus = modification.getOrCreateTag().getInt("oniutils_bonus");
    }

    @Override
    public void init() {
        int i = (this.width - 180) / 2;
        int j = (this.height - 150) / 2;

        ProgressOption slider = new ProgressOption("oniutils.gui.items.modification.bonus", -maxBonus, maxBonus, 1, gameSettings -> (double) bonus, (setting, value) -> bonus = value.intValue(), (gameSettings, sliderPercentageOption1) -> new TranslatableComponent("oniutils.gui.items.modification.bonus", sliderPercentageOption1.get(gameSettings)));
        sliderWidget = slider.createButton(Minecraft.getInstance().options, i, j, 180);
        addRenderableWidget(sliderWidget);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (minecraft == null) return;

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        matrixStack.pushPose();
        matrixStack.translate(0.0F, 0.0F, 100.0F);
        int i = (this.width - 20) / 2;
        int j = (this.height) / 2;
        minecraft.getItemRenderer().renderGuiItem(modification, i, j);
        matrixStack.popPose();
    }

    @Override
    public void removed() {
        super.removed();
        ONINetworking.sendToServer(new PacketModification(modification, bonus, ONIConstants.PacketType.MODIFICATION_DATA));
    }

    public static void open(ItemStack modification, int maxBonus) {
        Minecraft.getInstance().setScreen(null);
        Minecraft.getInstance().setScreen(new ONIModificationGUI(modification, maxBonus));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
