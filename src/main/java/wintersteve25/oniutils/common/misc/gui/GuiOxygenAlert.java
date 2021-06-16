package wintersteve25.oniutils.common.misc.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class GuiOxygenAlert extends Screen {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private int guiLeft;
    private int guiTop;

    public GuiOxygenAlert() {
        super(new StringTextComponent("note"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();

        guiLeft = (this.width - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;
    }

//    public void renderOverlay() {
//        ScaledResolution scaledRes = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
//        int scaledWdt = scaledRes.getScaledWidth() - 102;
//        int scaledHgt = scaledRes.getScaledHeight() - 12;
//
//        GL11.glColor4f(1F, 1F, 1F, 1F);
//
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/mods/" + Reference.MOD_ID + "/textures/gui/manaBar.png"));
//
//        PlayerInformation playerInfo = PlayerInformation.forPlayer(this.mc.thePlayer);
//
//        int manaBarCap = playerInfo.getMaxMana();
//
//        if (manaBarCap > 0) {
//            short short1 = 101;
//            int currentMana = 1 + playerInfo.getMana();
//            this.drawTexturedModalRect(scaledWdt, scaledHgt, 0, 0, short1, 11);
//
//            if (currentMana > 0) {
//                this.drawTexturedModalRect(scaledWdt, scaledHgt + 1, 0, 11, currentMana, 21);
//            }
//        }
//
//        this.mc.renderEngine.resetBoundTexture();
//    }
}
