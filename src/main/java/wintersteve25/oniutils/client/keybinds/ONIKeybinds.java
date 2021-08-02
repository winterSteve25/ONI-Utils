package wintersteve25.oniutils.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ONIKeybinds {
    public static KeyBinding offManualGen;

    public static void init() {
        offManualGen = registerKeyBind("oniutils.offManualGen", GLFW.GLFW_KEY_RIGHT_SHIFT, InputMappings.Type.KEYSYM, KeyConflictContext.IN_GAME);
    }

    public static KeyBinding registerKeyBind(String name, int code, InputMappings.Type type, IKeyConflictContext conflictContext) {
        KeyBinding keyBinding = new KeyBinding(name, conflictContext, type, code, "oniutils.keybinds.category");
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
