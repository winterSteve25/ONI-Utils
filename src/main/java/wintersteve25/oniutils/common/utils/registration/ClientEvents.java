package wintersteve25.oniutils.common.utils.registration;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenGui;
import wintersteve25.oniutils.common.init.ONIBlocks;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static void init (final FMLClientSetupEvent event) {
        //GUI Attachments
        ScreenManager.register(ONIBlocks.COAL_GEN_CONTAINER.get(), CoalGenGui::new);
    }
}
