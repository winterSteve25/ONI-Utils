package wintersteve25.oniutils.common.capability.germ;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;

public class GermEventsHandler {
    public static void entityCapAttachEvent (AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity) {
            GermCapabilityProvider provider = new GermCapabilityProvider();
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
            event.addListener(provider::invalidate);

            ONIUtils.LOGGER.info("Added germ capability to Entities/Players");
        }
    }

    public static void itemCapAttachEvent (AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack entity = event.getObject();
        if (entity != null) {
            GermCapabilityProvider provider = new GermCapabilityProvider();
            event.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
            event.addListener(provider::invalidate);

            ONIUtils.LOGGER.info("Added germ capability to ItemStacks");
        }
    }

    public static void teCapAttachEvent (AttachCapabilitiesEvent<TileEntity> e) {
        TileEntity tileAttached = e.getObject();
        if (tileAttached != null) {
            GermCapabilityProvider provider = new GermCapabilityProvider();
            e.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
            e.addListener(provider::invalidate);

            ONIUtils.LOGGER.info("Added germ capability to TileEntities");
        }
    }

    public static void infectOnInteractEntitySpecific (PlayerInteractEvent.EntityInteractSpecific event) {
        PlayerEntity player = event.getPlayer();
        ONIUtils.LOGGER.info("Player Interact, is player null?");
        if (player != null) {
            Entity target = event.getTarget();

            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
                target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
                    ONIUtils.LOGGER.info("does target have germs");
                    if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                        p.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    } else if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                        t.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectEntity", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    }
                });
            });
        }
    }

    public static void infectOnInteractEntity (PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        ONIUtils.LOGGER.info("Player Interact, is player null?");
        if (player != null) {
            Entity target = event.getTarget();

            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
                target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
                    ONIUtils.LOGGER.info("does target have germs");
                    if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                        p.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    } else if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                        t.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectEntity", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    }
                });
            });
        }
    }

    public static void infectOnPickItem (EntityItemPickupEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            ItemStack target = event.getItem().getItem();

            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
                target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
                    if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                        p.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    } else if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                        t.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    }
                });
            });
        }
    }

    public static void infectOnTossItem (ItemTossEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            ItemStack target = event.getEntityItem().getItem();

            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
                target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
                    if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                        p.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    } else if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                        t.addGerm(t.getGermType(), t.getGermAmount());
                        player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                    }
                });
            });
        }
    }

    public static void infectOnTileInteract (PlayerInteractEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {

            BlockPos pos = event.getPos();
            TileEntity target = event.getWorld().getBlockEntity(pos);

            ONIUtils.LOGGER.info("is tile null?");
            if (target != null) {
                ONIUtils.LOGGER.info("tile is not null");
                player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
                    target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
                        if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                            p.addGerm(t.getGermType(), t.getGermAmount());
                            player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                        } else if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                            t.addGerm(t.getGermType(), t.getGermAmount());
                            player.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", t.getGermAmount(), t.getGermType().getName().replace('_', ' ')), true);
                        }
                    });
                });
            }
        }
    }

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {

                EnumGermTypes germTypes = p.getGermType();
                int germAmount = p.getGermAmount();

                if (germAmount > 0 && germTypes != EnumGermTypes.NOTHING) {
                    int newGermAmount = germAmount * 2;
                    p.addGerm(germTypes, newGermAmount);

                    if (germTypes == EnumGermTypes.FOODPOISON && germAmount > 50000) {
                        player.addEffect(new EffectInstance(Effects.HUNGER));
                        if (germAmount > 85000) {
                            player.addEffect(new EffectInstance(Effects.WEAKNESS));
                        }
                    }

                    if (germTypes == EnumGermTypes.SLIMELUNG && germAmount > 50000) {
                        player.addEffect(new EffectInstance(Effects.WEAKNESS));
                    }

                    if (germTypes == EnumGermTypes.ZOMBIESPORES && germAmount > 100000) {
                        player.addEffect(new EffectInstance(Effects.POISON));
                        if (germAmount > 150000) {
                            player.addEffect(new EffectInstance(Effects.WITHER));
                        }
                    }
                }
            });
        }
    }
}
