package wintersteve25.oniutils.common.init;

import mekanism.common.registration.impl.SoundEventRegistryObject;
import net.minecraft.sounds.SoundEvent;
import wintersteve25.oniutils.common.utils.helpers.RegistryHelper;

public class ONISounds {
    public static SoundEventRegistryObject<SoundEvent> COAL_GEN_SOUND;

    public static void register() {
        COAL_GEN_SOUND = RegistryHelper.registerSounds("coal_gen");
    }
}
