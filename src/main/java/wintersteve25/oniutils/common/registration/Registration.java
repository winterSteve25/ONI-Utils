package wintersteve25.oniutils.common.registration;

import mekanism.common.registration.impl.FluidDeferredRegister;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
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
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ONIUtils.MODID);
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ONIUtils.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ONIUtils.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ONIUtils.MODID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ONIUtils.MODID);
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ONIUtils.MODID);
    public static final FluidDeferredRegister FLUID = new FluidDeferredRegister(ONIUtils.MODID);

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
                RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), b::get, ONIBlocks.blockList.get(b));
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