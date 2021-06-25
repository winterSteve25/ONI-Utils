package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.init.ONIEffects;

public class GasEventsHandler {
    public static void chunkAttach(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            if (!chunk.getCapability(GasCapability.GAS_CAPABILITY).isPresent()) {
                GasCapabilityProvider provider = new GasCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void entityAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            if (!entity.getCapability(GasCapability.GAS_CAPABILITY).isPresent()) {
                GasCapabilityProvider provider = new GasCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void tick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            if (!player.getCommandSenderWorld().isClientSide()) {
                player.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(p -> {
                    Chunk chunk = (Chunk) player.getCommandSenderWorld().getChunk(player.blockPosition());
                    chunk.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(c -> {
                        if (c.getGas().containsKey(p.getRequiredType())) {
                            player.addEffect(new EffectInstance(ONIEffects.OXYGENATED, 400));
                            c.removeGas(p.getRequiredType(), 0.25);
                            p.addGas(p.getRequiredType(), 0.25);
                        }
                    });
                });
            }
        }
    }
}
