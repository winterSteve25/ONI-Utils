package wintersteve25.oniutils.common.capability.gas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.gas.api.EnumGasTypes;
import wintersteve25.oniutils.common.capability.gas.api.ICustomGasProvider;
import wintersteve25.oniutils.common.capability.gas.api.IGas;
import wintersteve25.oniutils.common.init.ONIConfig;

import java.util.concurrent.atomic.AtomicInteger;

public class GasEventsHandler {
    private static AtomicInteger breathTimer = new AtomicInteger(20);

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
        if (entity instanceof PlayerEntity) {
            if (!entity.getCapability(GasCapability.GAS_CAPABILITY).isPresent()) {
                GasCapabilityProvider provider = new GasCapabilityProvider(EnumGasTypes.OXYGEN, 5000);
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                event.addListener(provider::invalidate);
            }
        }

        if (entity != null) {
            if (!(entity instanceof ICustomGasProvider)) {
                if (!(entity instanceof PlayerEntity)) {
                    if (!entity.getCapability(GasCapability.GAS_CAPABILITY).isPresent()) {
                        GasCapabilityProvider provider = new GasCapabilityProvider(EnumGasTypes.OXYGEN, 4000);
                        event.addCapability(new ResourceLocation(ONIUtils.MODID, "gas"), provider);
                        event.addListener(provider::invalidate);
                    }
                }
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<IGas> capability = event.getOriginal().getCapability(GasCapability.GAS_CAPABILITY);
            capability.ifPresent(oldStore -> {
                event.getPlayer().getCapability(GasCapability.GAS_CAPABILITY).ifPresent(newStore -> {
                    newStore.setGas(oldStore.getGas());
                });
            });
        }
    }

    public static void tick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            if (!player.world.isRemote()) {
                player.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(p -> {
                    Chunk chunk = (Chunk) player.world.getChunk(player.getPosition());
                    chunk.getCapability(GasCapability.GAS_CAPABILITY).ifPresent(c -> {
//                        ONIUtils.LOGGER.info("PlayerGas" + p.getGas());
//                        ONIUtils.LOGGER.info("ChunkGas" + c.getGas());

                        breathTimer.getAndDecrement();
                        if (breathTimer.get() < 0) {
                            for (EnumGasTypes gas : c.getGas().keySet()) {

                                if (p.addGas(gas, (c.getGas().get(gas) / c.getGasAmountTotal()) * ONIConfig.PLAYER_BREATH_AMOUNT.get())) {
                                    c.removeGas(gas, (c.getGas().get(gas) / c.getGasAmountTotal()) * ONIConfig.PLAYER_BREATH_AMOUNT.get());
                                    c.addGas(EnumGasTypes.CO2, ONIConfig.PLAYER_REQUIRED_OXYGEN_AMOUNT.get()/2);
                                }

                                p.updatePressure();
                                c.updatePressure();
                            }
                            p.removeGas(p.getRequiredType(), ONIConfig.PLAYER_REQUIRED_OXYGEN_AMOUNT.get());
                            p.updatePressure();
                            breathTimer.set(20);
                        }

                        if (p.getPressureMap().get(EnumGasTypes.OXYGEN) != null) {
                            if (p.getPressureMap().get(EnumGasTypes.OXYGEN) < 5) {
                                player.addPotionEffect(new EffectInstance(Effects.BLINDNESS));
                            }

                            if (p.getPressureMap().get(EnumGasTypes.OXYGEN) < 1) {
                                player.attackEntityFrom(ONIUtils.oxygenDamage, 2);
                            }
                        }

                        if (p.getPressureMap().get(EnumGasTypes.POLLUTED_OXYGEN) != null) {
                            if (p.getPressureMap().get(EnumGasTypes.POLLUTED_OXYGEN) > 20) {
                                player.addPotionEffect(new EffectInstance(Effects.HUNGER));
                            }

                            if (p.getPressureMap().get(EnumGasTypes.POLLUTED_OXYGEN) > 40) {
                                player.addPotionEffect(new EffectInstance(Effects.POISON));
                            }

                            if (p.getPressureMap().get(EnumGasTypes.POLLUTED_OXYGEN) > 95) {
                                player.attackEntityFrom(ONIUtils.gas, 2);
                            }
                        }

                        if(p.getPressureMap().get(EnumGasTypes.CO2) != null) {
                            if (p.getPressureMap().get(EnumGasTypes.CO2) > 20) {
                                player.addPotionEffect(new EffectInstance(Effects.WEAKNESS));
                            }

                            if (p.getPressureMap().get(EnumGasTypes.CO2) > 30) {
                                player.addPotionEffect(new EffectInstance(Effects.POISON));
                            }

                            if (p.getPressureMap().get(EnumGasTypes.CO2) > 95) {
                                player.attackEntityFrom(ONIUtils.gas, 2);
                            }
                        }
                    });
                });
            }
        }
    }
}
