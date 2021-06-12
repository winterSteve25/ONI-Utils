package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.gas.api.EnumGasTypes;

import java.util.concurrent.atomic.AtomicInteger;

public class GasEventsHandler {
    public static void chunkCapAttachEvent (AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            GasCapabilityProvider provider = new GasCapabilityProvider();
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
            event.addListener(provider::invalidate);

            ONIUtils.LOGGER.info("Added gas capability to Chunk");
        }
    }

    public static void entityCapAttachEvent (AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            GasCapabilityProvider provider = new GasCapabilityProvider();
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
            event.addListener(provider::invalidate);

            ONIUtils.LOGGER.info("Added gas capability to Entities/Players");
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            player.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(p -> {
                ChunkPos chunkPos = new ChunkPos(player.xChunk, player.zChunk);
                Chunk chunk = player.getCommandSenderWorld().getChunk(chunkPos.x, chunkPos.z);
                AtomicInteger timer = new AtomicInteger(1000);

                chunk.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(c -> {
                    EnumGasTypes gas = c.getGasType();
                    int amount = c.getGasAmount();
                    if (gas == EnumGasTypes.OXYGEN && amount > 0) {
                        c.removeGas(1);
                        if (timer.get() < 1000) {
                            timer.getAndIncrement();
                        }
                    } else if (gas != EnumGasTypes.OXYGEN) {
                        timer.getAndDecrement();
                        if (timer.get() < 800) {
                            player.addEffect(new EffectInstance(Effects.WEAKNESS));
                        } else if (timer.get() < 500) {
                            player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN));
                        } else if (timer.get() <= 300) {
                            player.addEffect(new EffectInstance(Effects.CONFUSION));
                        } else if (timer.get() <= 0) {
                            player.addEffect(new EffectInstance(Effects.HARM));
                        }
                    }
                });
            });
        }
    }
}
