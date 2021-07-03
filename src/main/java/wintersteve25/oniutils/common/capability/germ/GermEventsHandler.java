package wintersteve25.oniutils.common.capability.germ;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.germ.api.IGermCapProvider;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.init.ONIConfig;

import java.util.List;

public class GermEventsHandler {
    public static void entityCapAttachEvent (AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity != null) {
            if (!entity.getCapability(GermCapability.GERM_CAPABILITY).isPresent()) {
                GermCapabilityProvider provider = new GermCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void itemCapAttachEvent (AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack entity = event.getObject();
        if (entity != null) {
            if (!entity.getCapability(GermCapability.GERM_CAPABILITY).isPresent()) {
                GermCapabilityProvider provider = new GermCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }

    public static void itemToolTipEvent (ItemTooltipEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            ItemStack itemStack = event.getItemStack();
            List<ITextComponent> tooltip = event.getToolTip();
            if (itemStack.isEmpty()) {
                return;
            }

            itemStack.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(i -> {
                tooltip.add(new TranslationTextComponent("oniutils.tooltips.germs.itemGerms", Integer.toString(i.getGermAmount()), i.getGermType().getName().replace('_', ' ')));
            });
        }
    }

    public static void teCapAttachEvent (AttachCapabilitiesEvent<TileEntity> e) {
        TileEntity tileAttached = e.getObject();
        if (tileAttached != null) {
            if (!(tileAttached instanceof IGermCapProvider)) {
                if (!tileAttached.getCapability(GermCapability.GERM_CAPABILITY).isPresent()) {
                    GermCapabilityProvider provider = new GermCapabilityProvider();
                    e.addCapability(new ResourceLocation(ONIUtils.MODID, "germs"), provider);
                    e.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void infectOnInteractEntitySpecific (PlayerInteractEvent.EntityInteractSpecific event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if(!player.world.isRemote()) {
                Entity target = event.getTarget();

                player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                    target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
                        if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                            t.addGerm(p.getGermType(), p.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectEntity", Integer.toString(p.getGermAmount()), p.getGermType().getName().replace('_', ' ')), true);
                            return;
                        } else if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                            p.addGerm(t.getGermType(), t.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(t.getGermAmount()), t.getGermType().getName().replace('_', ' ')), true);
                        }
                    });
                });
            }
        }
    }

    public static void infectOnInteractEntity (PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if(!player.world.isRemote()) {
                Entity target = event.getTarget();

                player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                    target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
                        if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                            t.addGerm(p.getGermType(), p.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectEntity", Integer.toString(p.getGermAmount()), p.getGermType().getName().replace('_', ' ')), true);
                            return;
                        } else if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                            p.addGerm(t.getGermType(), t.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(t.getGermAmount()), t.getGermType().getName().replace('_', ' ')), true);
                        }
                    });
                });
            }
        }
    }

    public static void infectOnPickItem (EntityItemPickupEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if(!player.world.isRemote()) {
                ItemStack target = event.getItem().getItem();

                player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                    target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
                        if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                            t.addGerm(p.getGermType(), p.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", Integer.toString(p.getGermAmount()), p.getGermType().getName().replace('_', ' ')), true);
                            return;
                        } else if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                            p.addGerm(t.getGermType(), t.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(t.getGermAmount()), t.getGermType().getName().replace('_', ' ')), true);
                        }
                    });
                });
            }
        }
    }

    public static void infectOnTossItem (ItemTossEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if(!player.world.isRemote()) {
                ItemStack target = event.getEntityItem().getItem();

                player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                    target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
                        if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                            t.addGerm(p.getGermType(), p.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", Integer.toString(p.getGermAmount()), p.getGermType().getName().replace('_', ' ')), true);
                            return;
                        } else if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                            p.addGerm(t.getGermType(), t.getGermAmount());
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(t.getGermAmount()), t.getGermType().getName().replace('_', ' ')), true);
                        }
                    });
                });
            }
        }
    }

    public static void infectOnTileInteract (PlayerInteractEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player != null) {
            if(!player.world.isRemote()) {
                BlockPos pos = event.getPos();
                TileEntity target = event.getWorld().getTileEntity(pos);
                if (target != null) {
                    player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                        target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
                            if (p.getGermType() != EnumGermTypes.NOTHING && p.getGermAmount() > 0) {
                                t.addGerm(p.getGermType(), p.getGermAmount());
                                player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectItem", Integer.toString(p.getGermAmount()), p.getGermType().getName().replace('_', ' ')), true);
                                return;
                            } else if (t.getGermType() != EnumGermTypes.NOTHING && t.getGermAmount() > 0) {
                                p.addGerm(t.getGermType(), t.getGermAmount());
                                player.sendStatusMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(t.getGermAmount()), t.getGermType().getName().replace('_', ' ')), true);
                            }
                        });
                    });
                }
            }
        }
    }

    public static void keepGermWhilePlaced (BlockEvent.EntityPlaceEvent event) {
        BlockPos pos = event.getPos();
        TileEntity tileEntity = event.getWorld().getTileEntity(pos);

        if (event.getEntity() != null) {
            ItemStack itemStack = event.getEntity().getHeldEquipment().iterator().next();

            if (tileEntity != null) {
                itemStack.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(stack -> {
                    tileEntity.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(te -> {
                        te.setGerm(stack.getGermType(), stack.getGermAmount());
                    });
                });
            }
        }
    }

    public static void keepGermWhenTileBroken (BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        TileEntity tileEntity = event.getWorld().getTileEntity(pos);
    }

    private static int germDupSpeed = ONIConfig.GERM_DUP_SPEED_PLAYER.get();

    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (player != null) {
            if(!player.world.isRemote()) {
                player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                    germDupSpeed--;

                    EnumGermTypes germTypes = p.getGermType();
                    int germAmount = p.getGermAmount();

                    // ONIUtils.LOGGER.info(germTypes.getName() + ", " + germAmount);

                    if (germAmount > 0 && germTypes != EnumGermTypes.NOTHING) {

                        if (germDupSpeed < 0) {
                            if (germAmount < ONIConfig.GERM_STOP_DUP_AMOUNT.get()) {
                                p.addGerm(germTypes, 1);
                            }
                            germDupSpeed = ONIConfig.GERM_DUP_SPEED_PLAYER.get();
                        }

                        if (germTypes == EnumGermTypes.FOODPOISON && germAmount > 50000) {
                            player.addPotionEffect(new EffectInstance(Effects.HUNGER));
                            if (germAmount > 85000) {
                                player.addPotionEffect(new EffectInstance(Effects.WEAKNESS));
                                if (germAmount > 2000000) {
                                    player.attackEntityFrom(ONIUtils.germDamage, 6);
                                }
                            }
                        }

                        if (germTypes == EnumGermTypes.SLIMELUNG && germAmount > 50000) {
                            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS));
                            if (germAmount > 85000) {
                                player.addPotionEffect(new EffectInstance(Effects.WEAKNESS));
                                if (germAmount > 2000000) {
                                    player.attackEntityFrom(ONIUtils.germDamage, 6);
                                }
                            }
                        }

                        if (germTypes == EnumGermTypes.ZOMBIESPORES && germAmount > 100000) {
                            player.addPotionEffect(new EffectInstance(Effects.POISON));
                            if (germAmount > 1500000) {
                                player.attackEntityFrom(ONIUtils.germDamage, 6);
                            }
                        }
                    }
                });
            }
        }
    }
}