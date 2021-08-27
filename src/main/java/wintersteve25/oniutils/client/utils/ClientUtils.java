package wintersteve25.oniutils.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientUtils {
    public static void openGui(Screen screenIn) {
        Minecraft.getInstance().displayGuiScreen(screenIn);
    }
}
