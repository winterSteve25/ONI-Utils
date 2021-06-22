package wintersteve25.oniutils.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ONIKeybinds {
    public static KeyBinding offManualGen;

    public static void init() {
        offManualGen = registerKeyBind("oniutils.offManualGen", GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    public static KeyBinding registerKeyBind(String name, int key) {
        KeyBinding keyBinding = new KeyBinding(name, key, "oniutils.keybinds.category");
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
