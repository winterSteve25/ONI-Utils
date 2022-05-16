package wintersteve25.oniutils.common.registries;

import mekanism.common.registration.impl.SoundEventDeferredRegister;
import mekanism.common.registration.impl.SoundEventRegistryObject;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import wintersteve25.oniutils.ONIUtils;

public class ONISounds {
    public static final SoundEventDeferredRegister SOUND = new SoundEventDeferredRegister(ONIUtils.MODID);

    public static SoundEventRegistryObject<SoundEvent> COAL_GEN_SOUND;

    public static void register(IEventBus bus) {
        SOUND.register(bus);
        COAL_GEN_SOUND = SOUND.register("coal_gen");
    }
}
