package wintersteve25.oniutils.common.init;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.utils.helpers.RegistryHelper;

public class ONISounds {
    public static RegistryObject<SoundEvent> COAL_GEN_SOUND;

    public static void register() {
        COAL_GEN_SOUND = RegistryHelper.registerSounds("coal_gen", () -> RegistryHelper.createSound("coal_gen"));
    }
}
