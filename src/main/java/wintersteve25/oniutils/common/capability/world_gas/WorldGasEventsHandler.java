package wintersteve25.oniutils.common.capability.world_gas;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.world_gas.api.IWorldGas;

public class WorldGasEventsHandler {
    public static void chunkCapAttachEvent(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (!chunk.getWorld().isRemote()) {
            if (!chunk.getCapability(WorldGasCapability.WORLD_GAS_CAP).isPresent()) {
                WorldGasCapProv provider = new WorldGasCapProv();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "world_gas"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void worldTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();
        if (!world.isRemote()) {
            BlockPos pos = player.getPosition();
            Chunk chunk = world.getChunkAt(pos);
            chunk.getCapability(WorldGasCapability.WORLD_GAS_CAP).ifPresent(IWorldGas::tick);
        }
    }
}
