package wintersteve25.oniutils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.oniutils.common.registries.ONIBlocks;
import wintersteve25.oniutils.common.registries.ONIConfig;
import wintersteve25.oniutils.common.events.ONIServerEventsHandler;
import wintersteve25.oniutils.common.registration.Registration;

@Mod(ONIUtils.MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ONIUtils() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ONIConfig.Server.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ONIConfig.Client.CLIENT_CONFIG);
        Registration.init();

        modEventBus.addListener(ONIServerEventsHandler::commonSetup);
        forgeEventBus.addListener(ONIServerEventsHandler::command);

        MinecraftForge.EVENT_BUS.register(this);
    }

    //item group
    public static final CreativeModeTab creativeTab = new CreativeModeTab("oniutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ONIBlocks.NonFunctionals.IGNEOUS_ROCK.asItem());
        }
    };
    public static Item.Properties defaultProperties() {
        return new Item.Properties().tab(ONIUtils.creativeTab);
    }

    //damage source
    public static final DamageSource oxygenDamage = new DamageSource("oniutils.oxygen").bypassArmor();
    public static final DamageSource gas = new DamageSource("oniutils.gas").bypassArmor();
    public static final DamageSource germDamage = new DamageSource("oniutils.germ").bypassArmor();
}
