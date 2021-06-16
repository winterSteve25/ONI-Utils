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
import wintersteve25.oniutils.common.capability.germ.GermsCapability;

public class GasEventsHandler {

    public static void chunkCapAttachEvent(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            if (!chunk.getCapability(GermsCapability.GERM_CAPABILITY).isPresent()) {
                GasCapabilityProvider provider = new GasCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void entityCapAttachEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            if (!entity.getCapability(GermsCapability.GERM_CAPABILITY).isPresent()) {
                GasCapabilityProvider provider = new GasCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            player.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(p -> {
                ChunkPos chunkPos = new ChunkPos(player.xChunk, player.zChunk);
                Chunk chunk = player.getCommandSenderWorld().getChunk(chunkPos.x, chunkPos.z);

                chunk.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(c -> {
                    p.removeGas(EnumGasTypes.OXYGEN, 1);

                    EnumGasTypes gas = c.getGasType();
                    int amount = c.getGasAmount();

                    c.addGas(EnumGasTypes.CARBONDIOXIDE, 1);

                    if (amount <= 0) {
                        c.setGas(EnumGasTypes.VACUUM, 1);
                    }

                    EnumGasTypes playerGas = p.getGasType();
                    int playerAmount = p.getGasAmount();

                    ONIUtils.LOGGER.info(gas.getName());
                    if (gas == EnumGasTypes.OXYGEN && amount > 0) {
                        //ONIUtils.LOGGER.info("removing o2 from chunk, adding o2 to player");
                        c.removeGas(1);
                        p.addGas(EnumGasTypes.OXYGEN, 1);
                    } if (gas != EnumGasTypes.OXYGEN) {
                        //ONIUtils.LOGGER.info(playerAmount);
                        if (playerGas != EnumGasTypes.OXYGEN && playerAmount < 1000) {
                            player.addEffect(new EffectInstance(Effects.WEAKNESS));
                            if (playerAmount < 800) {
                                player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 1, 2));
                                if (playerAmount < 300) {
                                    player.addEffect(new EffectInstance(Effects.WEAKNESS, 1, 3));
                                    if (playerAmount <= 0) {
                                        player.hurt(ONIUtils.oxygenDamage, 3);
                                    }
                                }
                            }
                        }
                    }
                });
            });
        }
    }
}
