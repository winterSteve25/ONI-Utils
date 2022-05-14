package wintersteve25.oniutils.common.registration;

import mekanism.common.registration.impl.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.ONIIRegistryObject;
import wintersteve25.oniutils.common.contents.base.ONIBaseEffect;
import wintersteve25.oniutils.common.init.*;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;
import wintersteve25.oniutils.common.utils.helpers.RegistryHelper;

public class Registration {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(ONIUtils.MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(ONIUtils.MODID);
    public static final FluidDeferredRegister FLUID = new FluidDeferredRegister(ONIUtils.MODID);
    public static final SoundEventDeferredRegister SOUND = new SoundEventDeferredRegister(ONIUtils.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ONIUtils.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ONIUtils.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TE.register(eventBus);
        CONTAINER.register(eventBus);
        EFFECTS.register(eventBus);
        SOUND.register(eventBus);
        FLUID.register(eventBus);

        ONIBlocks.register();
        registerBlocks();
        ONIEffects.register();
        registerPotions();
        ONISounds.register();
        ONIItems.register();
        registerItems();
        ONIGases.register(eventBus);

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }

    public static void registerBlocks() {
        for (ONIIRegistryObject<Block> b : ONIBlocks.blockList.keySet()) {
            if (ONIBlocks.blockList.get(b) != null) {
                Registration.BLOCKS.register(MiscHelper.langToReg(b.getRegName()), b::get, block -> ONIBlocks.blockList.get(b));
            } else {
                RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), b::get);
            }
        }
    }

    public static void registerPotions() {
        for (ONIBaseEffect e : ONIEffects.effectList) {
            RegistryHelper.registerEffects(MiscHelper.langToReg(e.getRegName()), () -> e);
        }
    }

    public static void registerItems() {
        for (ONIIRegistryObject<Item> item : ONIItems.itemRegistryList) {
            RegistryHelper.registerItem(MiscHelper.langToReg(item.getRegName()), item.get());
        }
    }
}