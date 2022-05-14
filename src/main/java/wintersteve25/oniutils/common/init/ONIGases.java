package wintersteve25.oniutils.common.init;

import mekanism.api.chemical.gas.Gas;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.Element;

import java.util.ArrayList;
import java.util.List;

public class ONIGases {
    public static final GasDeferredRegister GASES = new GasDeferredRegister(ONIUtils.MODID);
    public static final List<GasRegistryObject<Gas>> ELEMENT_GASES = new ArrayList<>();

    public static void register(IEventBus bus) {
        GASES.register(bus);
        registerChemistryElements();
    }

    private static void registerChemistryElements() {
        for (Element element : Element.values()) {
            ELEMENT_GASES.add(GASES.register(element));
        }
    }
}
