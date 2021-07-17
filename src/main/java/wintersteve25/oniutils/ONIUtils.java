package wintersteve25.oniutils;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.utils.registration.ONIGeneralEventsHandler;
import wintersteve25.oniutils.common.utils.registration.Registration;
//import wintersteve25.oniutils.common.compat.potr.AdPotherAddonEventHandlers;

@Mod(ONIUtils.MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ONIUtils() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ONIConfig.SERVER_CONFIG);
        Registration.init();

        modEventBus.addListener(ONIGeneralEventsHandler::preInit);
        modEventBus.addListener(ONIGeneralEventsHandler::clientPreInit);
        forgeEventBus.addListener(ONIGeneralEventsHandler::serverInit);

        MinecraftForge.EVENT_BUS.register(this);
    }

    //item group
    public static final ItemGroup creativeTab = new ItemGroup("oniutils") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ONIBlocks.IgneousRock.get());
        }
    };

    //damage source
    public static final DamageSource oxygenDamage = new DamageSource("oniutils.oxygen").setDamageBypassesArmor();
    public static final DamageSource gas = new DamageSource("oniutils.gas").setDamageBypassesArmor();
    public static final DamageSource germDamage = new DamageSource("oniutils.germ").setDamageBypassesArmor();
}
