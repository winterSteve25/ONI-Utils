package wintersteve25.oniutils.common.capability.morale;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.morale.api.IMoraleModifierBlock;
import wintersteve25.oniutils.common.init.ONIConfig;

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

    public static void playerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
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

    public static void placeBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        BlockState state = event.getPlacedBlock();
        if (entity != null) {
            World world = entity.getEntityWorld();
            if (!world.isRemote()) {
                if (state.getBlock() instanceof IMoraleModifierBlock) {
                    IMoraleModifierBlock moraleModifierBlock = (IMoraleModifierBlock) state.getBlock();
                    for (PlayerEntity player : world.getPlayers()) {
                        Chunk chunk = world.getChunkAt(player.getPosition());
                        player.getCapability(MoraleCapability.MORALE_CAPABILITY).ifPresent(p -> {
                            chunk.getCapability(MoraleCapability.MORALE_CAPABILITY).ifPresent(c -> {
                                p.setMorale(c.getMorale() + moraleModifierBlock.moraleModifier());
                            });

                            //TODO: Implement stress reaction
                            if (p.getMorale() <= ONIConfig.MORALE_FOR_STRESS.get()) {

                            }
                        });
                    }
                }
            }
        }
    }
}
