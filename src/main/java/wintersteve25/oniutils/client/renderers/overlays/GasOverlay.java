package wintersteve25.oniutils.client.renderers.overlays;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.lwjgl.opengl.GL11;
import wintersteve25.oniutils.ONIUtils;

public class GasOverlay {
//    @OnlyIn(Dist.CLIENT)
//    public static void renderHUD(MatrixStack ms, PlayerEntity player, ItemStack stack) {
//        ResourceLocation textureHud = new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/gas");
//
//        int u = Math.max(1, getVariant(stack)) * 9 - 9;
//        int v = 0;
//
//        Minecraft mc = Minecraft.getInstance();
//        mc.textureManager.bind(textureHud);
//        int xo = mc.getWindow().getGuiScaledWidth() / 2 + 10;
//        int x = xo;
//        int y = mc.getWindow().getGuiScaledHeight() - ForgeIngameGui.right_height;
//        ForgeIngameGui.right_height += 10;
//
//        int left = ItemNBTHelper.getInt(stack, TAG_TIME_LEFT, MAX_FLY_TIME);
//
//        int segTime = MAX_FLY_TIME / 10;
//        int segs = left / segTime + 1;
//        int last = left % segTime;
//
//        for (int i = 0; i < segs; i++) {
//            float trans = 1F;
//            if (i == segs - 1) {
//                trans = (float) last / (float) segTime;
//                RenderSystem.enableBlend();
//                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//                RenderSystem.disableAlphaTest();
//            }
//
//            RenderSystem.color4f(1F, 1F, 1F, trans);
//            RenderHelper.drawTexturedModalRect(ms, x, y, u, v, 9, 9);
//            x += 8;
//        }
//
//        if (player.abilities.isFlying) {
//            int width = ItemNBTHelper.getInt(stack, TAG_DASH_COOLDOWN, 0);
//            RenderSystem.color4f(1F, 1F, 1F, 1F);
//            if (width > 0) {
//                AbstractGui.fill(ms, xo, y - 2, xo + 80, y - 1, 0x88000000);
//            }
//            AbstractGui.fill(ms, xo, y - 2, xo + width, y - 1, 0xFFFFFFFF);
//        }
//
//        RenderSystem.enableAlphaTest();
//        RenderSystem.color4f(1F, 1F, 1F, 1F);
//        mc.textureManager.bind(AbstractGui.GUI_ICONS_LOCATION);
//    }
}
