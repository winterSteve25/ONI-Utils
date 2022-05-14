package wintersteve25.oniutils.common.init;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import wintersteve25.oniutils.common.data.capabilities.germ.api.IGerms;
import wintersteve25.oniutils.common.data.capabilities.plasma.api.IPlasma;
import wintersteve25.oniutils.common.data.capabilities.player_data.api.IPlayerData;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.IWorldGas;

public class ONICapabilities {
    public static Capability<IGerms> GERMS = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IPlayerData> PLAYER = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IPlasma> PLASMA = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IWorldGas> GAS = CapabilityManager.get(new CapabilityToken<>(){});
}
