package wintersteve25.oniutils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.registration.Registration;

import static wintersteve25.oniutils.ONIUtils.MODID;

@Mod(MODID)
public class ONIUtils {
    public static final String MODID = "oniutils";

    public ONIUtils() {
        Registration.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static final ItemGroup creativeTab = new ItemGroup("oniutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ONIBlocks.IgneousRock.get());
        }
    };

    public static final Logger LOGGER = LogManager.getLogger(MODID);
}
