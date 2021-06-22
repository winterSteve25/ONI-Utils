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
import wintersteve25.oniutils.common.utils.registration.ClientEvents;
import wintersteve25.oniutils.common.utils.registration.ONIGeneralEventHandlers;
import wintersteve25.oniutils.common.utils.registration.Registration;
//import wintersteve25.oniutils.common.compat.potr.AdPotherAddonEventHandlers;

@Mod(ONIUtils.MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ONIUtils() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ONIConfig.SERVER_CONFIG);
        Registration.init();

        modEventBus.addListener(ONIGeneralEventHandlers::preInit);
        modEventBus.addListener(ONIGeneralEventHandlers::clientPreInit);
        modEventBus.addListener(ClientEvents::init);

        MinecraftForge.EVENT_BUS.register(this);
    }

    //item group
    public static final ItemGroup creativeTab = new ItemGroup("oniutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ONIBlocks.IgneousRock.get());
        }
    };

    //damage source
    public static final DamageSource oxygenDamage = new DamageSource("oniutils.oxygen").bypassArmor().bypassInvul();
    public static final DamageSource germDamage = new DamageSource("oniutils.germ").bypassArmor().bypassInvul();
}
