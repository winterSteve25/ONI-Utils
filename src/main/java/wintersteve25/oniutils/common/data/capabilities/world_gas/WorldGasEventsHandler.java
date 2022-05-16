package wintersteve25.oniutils.common.data.capabilities.world_gas;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.data.capabilities.ONICapabilityProvider;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.IWorldGas;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.WorldGas;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.registries.ONIConfig;

public class WorldGasEventsHandler {
    public static void chunkCapAttachEvent(AttachCapabilitiesEvent<LevelChunk> event) {
        LevelChunk chunk = event.getObject();
        if (!chunk.getLevel().isClientSide() && !chunk.getCapability(ONICapabilities.GAS).isPresent()) {
            ONICapabilityProvider<IWorldGas> provider = new ONICapabilityProvider<>(WorldGas::new, ONICapabilities.GAS);
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "world_gas"), provider);
            event.addListener(provider::invalidate);
        }
    }

    private static int cooldown = ONIConfig.GAS_UPDATE_FREQUENCY.get();

    public static void worldTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.getCommandSenderWorld();
        if (!world.isClientSide() && event.side.isServer() && event.phase == TickEvent.Phase.END) {
            cooldown--;
            if (cooldown <= 0) {
                BlockPos pos = player.blockPosition();
                LevelChunk chunk = world.getChunkAt(pos);
                chunk.getCapability(ONICapabilities.GAS).ifPresent(IWorldGas::update);
                cooldown = ONIConfig.GAS_UPDATE_FREQUENCY.get();
            }
        }
    }
}
