package wintersteve25.oniutils.common.init;

import mekanism.api.chemical.gas.Gas;
import mekanism.common.lib.Color;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import wintersteve25.oniutils.ONIUtils;

public class ONIGases {
    public static final GasDeferredRegister GASES = new GasDeferredRegister(ONIUtils.MODID);
    public static final GasRegistryObject<Gas> NITROGEN = GASES.register("oxygen", 67118181);

    public static void register(IEventBus bus) {
        GASES.register(bus);
    }
}
