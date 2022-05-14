package wintersteve25.oniutils.client.keybinds;

import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ONIKeybinds {
    public static KeyMapping offManualGen;

    public static void init() {
        offManualGen = registerKeyBind("oniutils.offManualGen", GLFW.GLFW_KEY_RIGHT_SHIFT, InputConstants.Type.KEYSYM, KeyConflictContext.IN_GAME);
    }

    public static KeyMapping registerKeyBind(String name, int code, InputConstants.Type type, IKeyConflictContext conflictContext) {
        KeyMapping keyBinding = new KeyMapping(name, conflictContext, type, code, "oniutils.keybinds.category");
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
