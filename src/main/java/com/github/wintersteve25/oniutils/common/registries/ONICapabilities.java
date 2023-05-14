package com.github.wintersteve25.oniutils.common.registries;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.data.capabilities.germ.api.IGerms;
import com.github.wintersteve25.oniutils.common.data.capabilities.plasma.api.IPlasma;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.IPlayerData;
import com.github.wintersteve25.oniutils.common.data.capabilities.world_gas.api.IWorldGas;

public class ONICapabilities {
    public static Capability<IGerms> GERMS;
    public static Capability<IPlayerData> PLAYER;
    public static Capability<IPlasma> PLASMA;
    public static Capability<IWorldGas> GAS;

    public static void register(IEventBus bus) {
        bus.addListener(ONICapabilities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        ONIUtils.LOGGER.info("Registering capabilities");
        event.register(IGerms.class);
        event.register(IPlayerData.class);
        event.register(IPlasma.class);
        event.register(IWorldGas.class);

        GERMS = CapabilityManager.get(new CapabilityToken<>(){});
        PLAYER = CapabilityManager.get(new CapabilityToken<>(){});
        PLASMA = CapabilityManager.get(new CapabilityToken<>(){});
        GAS = CapabilityManager.get(new CapabilityToken<>(){});
    }
}
