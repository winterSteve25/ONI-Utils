package wintersteve25.oniutils.common.capability.morale;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;

public class MoraleEventsHandler {
    public static void playerAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.getCapability(MoraleCapability.MORALE_CAPABILITY).isPresent()) {
                MoraleCapabilityProvider provider = new MoraleCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "morale"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void chunkAttach(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            if (!chunk.getCapability(MoraleCapability.MORALE_CAPABILITY).isPresent()) {
                MoraleCapabilityProvider provider = new MoraleCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "morale"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            World world = player.getEntityWorld();
            if (!world.isRemote()) {
                Chunk chunk = world.getChunkAt(player.getPosition());
                chunk.getCapability(MoraleCapability.MORALE_CAPABILITY).ifPresent(c -> {
                    player.getCapability(MoraleCapability.MORALE_CAPABILITY).ifPresent(p -> {
                        p.setMorale(c.getMorale());
                    });
                });
            }
        }
    }
}
