package wintersteve25.oniutils;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.lib.registration.Registration;

import static wintersteve25.oniutils.ONIUtils.MODID;

@Mod(MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";
    public static final ResourceLocation GermCap = new ResourceLocation(MODID, "germ");

    public ONIUtils() {
        Registration.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ONIConfig.SERVER_CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Chunk> event) {

    }

    public static final ItemGroup creativeTab = new ItemGroup("oniutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ONIBlocks.IgneousRock.get());
        }
    };

    public static final Logger LOGGER = LogManager.getLogger(MODID);
}
