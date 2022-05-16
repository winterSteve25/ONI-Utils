package wintersteve25.oniutils.common.data.capabilities.player_data;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.data.capabilities.ONICapabilityProvider;
import wintersteve25.oniutils.common.data.capabilities.player_data.api.IMoraleProvider;
import wintersteve25.oniutils.common.data.capabilities.player_data.api.IPlayerData;
import wintersteve25.oniutils.common.data.capabilities.player_data.api.PlayerData;
import wintersteve25.oniutils.common.data.capabilities.player_data.api.TraitTypes;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.events.events.PlayerMovingEvent;

public class PlayerDataEventsHandler {
    public static void entityCapAttachEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (!(entity instanceof Player)) {
            return;
        }
        if (!entity.level.isClientSide() && !entity.getCapability(ONICapabilities.PLAYER).isPresent()) {
            ONICapabilityProvider<IPlayerData> provider = new ONICapabilityProvider<>(PlayerData::new, ONICapabilities.PLAYER);
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "traits"), provider);
            event.addListener(provider::invalidate);
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<IPlayerData> capability = event.getOriginal().getCapability(ONICapabilities.PLAYER);
            capability.ifPresent(oldStore -> {
                event.getPlayer().getCapability(ONICapabilities.PLAYER).ifPresent(newStore -> {
                    newStore.setTrait(oldStore.getTraits().get(0), oldStore.getTraits().get(1), oldStore.getTraits().get(2));
                    newStore.setGottenTrait(oldStore.getGottenTrait());
                });
            });
        }
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            if (!player.getCommandSenderWorld().isClientSide()) {
                player.getCapability(ONICapabilities.PLAYER).ifPresent(p -> {
                    player.sendMessage(new TranslatableComponent("oniutils.message.trait.traitInfo"), player.getUUID());
                    if (!p.getGottenTrait()) {

                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        int randomTrait = p.getTraits().get(0);
                        int goodTrait = p.getTraits().get(1);
                        int badTrait = p.getTraits().get(2);

                        traitBonus(randomTrait, goodTrait, badTrait, serverPlayer);

                        p.setGottenTrait(true);
                    }
                    for (int i = 0; i < 3; i++) {
                        player.sendMessage(new TranslatableComponent("oniutils.message.trait.gotTrait", p.getTraits().get(i)), player.getUUID());
                    }
                });
            }
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null) {
            Level world = player.getCommandSenderWorld();
            if (!world.isClientSide()) {
                player.getCapability(ONICapabilities.PLAYER).ifPresent(p -> {
                    int randomTrait = p.getTraits().get(0);
                    int goodTrait = p.getTraits().get(1);
                    int badTrait = p.getTraits().get(2);

                    //TODO: FIX THIS!!!

                    long time = world.getGameTime();

//                    switch (randomTrait) {
//                        case TraitTypes.EarlyBird:
//                            if ((time > 23460 && time < 24000) || (time > 0 && time < 2000)) {
//                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
//                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
//                                player.addPotionEffect(new EffectInstance(Effects.STRENGTH));
//                            }
//                            break;
//                        case TraitTypes.NightOwl:
//                            if (time > 13000 && time < 23031) {
//                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
//                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
//                                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 10));
//                            }
//                            break;
//                    }
//
//                    switch (goodTrait) {
//                        case TraitTypes.EarlyBird:
//                            if ((time > 23460 && time < 24000) || (time > 0 && time < 2000)) {
//                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
//                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
//                                player.addPotionEffect(new EffectInstance(Effects.STRENGTH));
//                            }
//                            break;
//                        case TraitTypes.NightOwl:
//                            if (time > 13000 && time < 23031) {
//                                player.addPotionEffect(new EffectInstance(Effects.HASTE, 10, 2));
//                                player.addPotionEffect(new EffectInstance(Effects.SPEED, 10, 1));
//                                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 10));
//                            }
//                            break;
//                    }
                });
            }
        }
    }

    public static void playerMove(PlayerMovingEvent event) {
        Player player = event.getPlayer();
        Level world = player.getCommandSenderWorld();
        BlockPos pos = player.blockPosition();
        if (!world.isClientSide()) {
            if (event.getMovement() == PlayerMovingEvent.MovementTypes.JUMP && event.getMovement() == PlayerMovingEvent.MovementTypes.SNEAK)
                return;
            player.getCapability(ONICapabilities.PLAYER).ifPresent((cap) -> {
                //Reset and redo calculation when moved
                cap.setBuildBonus(0);
                for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-9, 0, -9), pos.offset(9, 3, 9))) {
                    Block block = world.getBlockState(blockpos).getBlock();
                    if (block instanceof IMoraleProvider) {
                        IMoraleProvider moraleProvider = (IMoraleProvider) block;
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

    private static void traitBonus(int randomTrait, int goodTrait, int badTrait, ServerPlayer serverPlayer) {
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
