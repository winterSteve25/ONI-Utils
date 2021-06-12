package wintersteve25.oniutils;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.oniutils.common.capability.gas.GasCapability;
import wintersteve25.oniutils.common.capability.gas.GasEventsHandler;
import wintersteve25.oniutils.common.capability.germ.GermEventsHandler;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.lib.registration.Registration;

@Mod(ONIUtils.MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ONIUtils() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ONIConfig.SERVER_CONFIG);

        Registration.init();
        modEventBus.addListener(this::preInit);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final ItemGroup creativeTab = new ItemGroup("oniutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ONIBlocks.IgneousRock.get());
        }
    };

    public void preInit(FMLCommonSetupEvent evt) {
        GermsCapability.register();
        GasCapability.register();

        //germ events
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GermEventsHandler::entityCapAttachEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, GermEventsHandler::teCapAttachEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, GermEventsHandler::itemCapAttachEvent);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnInteractEntitySpecific);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnInteractEntity);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnPickItem);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTossItem);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTileInteract);
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::playerTickEvent);

        //gas events
        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, GasEventsHandler::chunkCapAttachEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GasEventsHandler::entityCapAttachEvent);
        MinecraftForge.EVENT_BUS.addListener(GasEventsHandler::playerTickEvent);
    }
}
