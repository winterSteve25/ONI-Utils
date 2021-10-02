package wintersteve25.oniutils.common.capability.oni_player_data;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIIMoraleProvider;
import wintersteve25.oniutils.common.capability.oni_player_data.api.ONIIPlayerData;
import wintersteve25.oniutils.common.capability.oni_player_data.api.TraitTypes;
import wintersteve25.oniutils.common.capability.oni_te_data.ONITEDataCapability;
import wintersteve25.oniutils.common.registration.PlayerMovingEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class ONIPlayerDataEventsHandler {
    public static void entityCapAttachEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            if (!entity.world.isRemote()) {
                if (!entity.getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP).isPresent()) {
                    ONIPlayerDataCapProv provider = new ONIPlayerDataCapProv();
                    event.addCapability(new ResourceLocation(ONIUtils.MODID, "traits"), provider);
                    event.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<ONIIPlayerData> capability = event.getOriginal().getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP);
            capability.ifPresent(oldStore -> {
                event.getPlayer().getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP).ifPresent(newStore -> {
                    newStore.setTrait(oldStore.getTraits().get(0), oldStore.getTraits().get(1), oldStore.getTraits().get(2));
                    newStore.setGottenTrait(oldStore.getGottenTrait());
                });
            });
        }
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (!player.getEntityWorld().isRemote()) {
                player.getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP).ifPresent(p -> {
                    player.sendMessage(new TranslationTextComponent("oniutils.message.trait.traitInfo"), player.getUniqueID());
                    if (!p.getGottenTrait()) {

                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                        int randomTrait = p.getTraits().get(0);
                        int goodTrait = p.getTraits().get(1);
                        int badTrait = p.getTraits().get(2);

                        traitBonus(randomTrait, goodTrait, badTrait, serverPlayer);

                        p.setGottenTrait(true);
                    }
                    for (int i = 0; i < 3; i++) {
                        player.sendMessage(new TranslationTextComponent("oniutils.message.trait.gotTrait", p.getTraits().get(i)), player.getUniqueID());
                    }
                });
            }
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            World world = player.getEntityWorld();
            if (!world.isRemote()) {
                player.getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP).ifPresent(p -> {
                    int randomTrait = p.getTraits().get(0);
                    int goodTrait = p.getTraits().get(1);
                    int badTrait = p.getTraits().get(2);

                    long time = world.getGameTime();

                    switch (randomTrait) {
                        case TraitTypes.EarlyBird:
                            if ((time > 23460 && time < 24000) || (time > 0 && time < 2000)) {
                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
                                player.addPotionEffect(new EffectInstance(Effects.STRENGTH));
                            }
                            break;
                        case TraitTypes.NightOwl:
                            if (time > 13000 && time < 23031) {
                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
                                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 10));
                            }
                            break;
                    }

                    switch (goodTrait) {
                        case TraitTypes.EarlyBird:
                            if ((time > 23460 && time < 24000) || (time > 0 && time < 2000)) {
                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
                                player.addPotionEffect(new EffectInstance(Effects.STRENGTH));
                            }
                            break;
                        case TraitTypes.NightOwl:
                            if (time > 13000 && time < 23031) {
                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
                                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 10));
                            }
                            break;
                    }
                });
            }
        }
    }

    public static void playerMove(PlayerMovingEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        BlockPos pos = player.getPosition();
        if (!world.isRemote()) {
            if (event.getMovement() == PlayerMovingEvent.MovementTypes.JUMP && event.getMovement() == PlayerMovingEvent.MovementTypes.SNEAK) return;
            player.getCapability(ONIPlayerDataCapability.ONI_PLAYER_CAP).ifPresent((cap) -> {
                //Reset and redo calculation when moved
                cap.setBuildBonus(0);
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-9, 0, -9), pos.add(9, 3, 9))) {
                    Block block = world.getBlockState(blockpos).getBlock();
                    if (block instanceof ONIIMoraleProvider) {
                        ONIIMoraleProvider moraleProvider = (ONIIMoraleProvider) block;
                        cap.setBuildBonus(cap.getBuildBonus() + moraleProvider.moraleModifier());
                    }
                }

//                int temperatureCached = cap.getTemperature();
//                cap.setTemperature(0);
//                AtomicInteger blockTemperatures = new AtomicInteger();
//                AtomicInteger amountTE = new AtomicInteger();
//
//                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-9, 0, -9), pos.add(9, 3, 9))) {
//                    TileEntity te = world.getTileEntity(blockpos);
//                    if (te == null) return;
//                    te.getCapability(ONITEDataCapability.ONI_TE_CAP).ifPresent((teCap) -> {
//                        blockTemperatures.addAndGet(teCap.getTemperature());
//                        amountTE.getAndIncrement();
//                    });
//                }
//
//                blockTemperatures.set(blockTemperatures.get()/amountTE.get());

            });
        }
    }

    private static void traitBonus(int randomTrait, int goodTrait, int badTrait, ServerPlayerEntity serverPlayer) {
        switch (randomTrait) {
        }
        switch (goodTrait) {
            case TraitTypes.Twinkletoes:
                APIUtils.setLevel("agility", serverPlayer, 7);
                break;
            case TraitTypes.Buff:
                APIUtils.setLevel("combat", serverPlayer, 5);
                break;
            case TraitTypes.MoleHands:
                APIUtils.setLevel("mining", serverPlayer, 5);
                break;
            case TraitTypes.GreaseMonkey:
                APIUtils.setLevel("machinery", serverPlayer, 5);
                break;
            case TraitTypes.QuickLearner:
                APIUtils.setLevel("science", serverPlayer, 5);
                break;
            case TraitTypes.InteriorDecorator:
                APIUtils.setLevel("creativity", serverPlayer, 5);
                break;
            case TraitTypes.Caregiver:
                APIUtils.setLevel("medicine", serverPlayer, 5);
                break;
        }
        switch (badTrait) {
        }
    }
}
