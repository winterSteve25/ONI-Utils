package wintersteve25.oniutils.common.utils.registration;

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
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.effects.ONIBaseEffect;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIEffects;
import wintersteve25.oniutils.common.init.ONISounds;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;
import wintersteve25.oniutils.common.utils.helper.RegistryHelper;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ONIUtils.MODID);
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ONIUtils.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.CONTAINERS, ONIUtils.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ONIUtils.MODID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ONIUtils.MODID);
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TE.register(eventBus);
        CONTAINER.register(eventBus);
        EFFECTS.register(eventBus);
        SOUND.register(eventBus);

        ONIBlocks.register();
        registerBlocks();
        ONIEffects.register();
        registerPotions();
        ONISounds.register();

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }

    public static void registerBlocks() {
        for (ONIBaseBlock b : ONIBlocks.blockList) {
            RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseBlock ndr : ONIBlocks.blockNoDataList) {
            RegistryHelper.register(MiscHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
        for (ONIBaseDirectional b : ONIBlocks.directionalList) {
            RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseDirectional ndr : ONIBlocks.directionalNoDataList) {
            RegistryHelper.register(MiscHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
    }

    public static void registerPotions() {
        for (ONIBaseEffect e : ONIEffects.effectList) {
            RegistryHelper.registerEffects(MiscHelper.langToReg(e.getRegName()), () -> e);
        }
    }
}
