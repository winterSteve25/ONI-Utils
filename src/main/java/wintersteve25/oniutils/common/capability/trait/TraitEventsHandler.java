package wintersteve25.oniutils.common.capability.trait;

import harmonised.pmmo.skills.Skill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.trait.api.ITrait;
import wintersteve25.oniutils.common.capability.trait.api.TraitTypes;

public class TraitEventsHandler {
    public static void entityCapAttachEvent (AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            if (!entity.world.isRemote()) {
                if (!entity.getCapability(TraitCapability.TRAIT_CAPABILITY).isPresent()) {
                    TraitCapabilityProvider provider = new TraitCapabilityProvider();
                    event.addCapability(new ResourceLocation(ONIUtils.MODID, "traits"), provider);
                    event.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            LazyOptional<ITrait> capability = event.getOriginal().getCapability(TraitCapability.TRAIT_CAPABILITY);
            capability.ifPresent(oldStore -> {
                event.getPlayer().getCapability(TraitCapability.TRAIT_CAPABILITY).ifPresent(newStore -> {
                    newStore.setTrait(oldStore.getTraits().get(0), oldStore.getTraits().get(1), oldStore.getTraits().get(2));
                    newStore.setGottenTrait(oldStore.getGottenTrait());
                });
            });
        }
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if (!player.world.isRemote()) {
                player.getCapability(TraitCapability.TRAIT_CAPABILITY).ifPresent(p -> {
                    player.sendMessage(new TranslationTextComponent("oniutils.message.trait.traitInfo"), player.getUniqueID());
                    if(!p.getGottenTrait()) {

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
            World world = player.world;
            if (!world.isRemote()) {
                player.getCapability(TraitCapability.TRAIT_CAPABILITY).ifPresent(p -> {
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

    @SuppressWarnings("deprecation")
    public static void traitBonus (int randomTrait, int goodTrait, int badTrait, ServerPlayerEntity serverPlayer) {
        switch (randomTrait) {
        }
        switch (goodTrait) {
            case TraitTypes.Twinkletoes:
                Skill.setLevel("agility", serverPlayer, 7);
                break;
            case TraitTypes.Buff:
                Skill.setLevel("combat", serverPlayer, 5);
                break;
            case TraitTypes.MoleHands:
                Skill.setLevel("mining", serverPlayer, 5);
                break;
            case TraitTypes.GreaseMonkey:
                Skill.setLevel("machinery", serverPlayer, 5);
                break;
            case TraitTypes.QuickLearner:
                Skill.setLevel("science", serverPlayer, 5);
                break;
            case TraitTypes.InteriorDecorator:
                Skill.setLevel("creativity", serverPlayer, 5);
                break;
            case TraitTypes.Caregiver:
                Skill.setLevel("medicine", serverPlayer, 5);
                break;
        }
        switch (badTrait) {
        }
    }
}
