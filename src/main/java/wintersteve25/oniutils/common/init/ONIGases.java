package wintersteve25.oniutils.common.init;

import mekanism.common.registration.impl.GasDeferredRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.world_gas.api.chemistry.Element;

public class ONIGases {
    public static final GasDeferredRegister GASES = new GasDeferredRegister(ONIUtils.MODID);

    public static void register(IEventBus bus) {
        registerChemistryElements();
        GASES.register(bus);
    }

    private static void registerChemistryElements() {
        for (Element element : Element.values()) {
            GASES.register(element);
        }
    }
}
