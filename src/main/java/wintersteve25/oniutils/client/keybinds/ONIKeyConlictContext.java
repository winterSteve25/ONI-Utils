package wintersteve25.oniutils.client.keybinds;

import net.minecraftforge.client.settings.IKeyConflictContext;

/**
 * This class is currently not used
 */

public class ONIKeyConlictContext implements IKeyConflictContext {
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean conflicts(IKeyConflictContext other) {
        return false;
    }
}
