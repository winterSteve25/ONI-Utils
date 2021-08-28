package wintersteve25.oniutils.common.registration;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.keybinds.ONIKeybinds;
import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibBlockRendererBase;
import wintersteve25.oniutils.client.renderers.geckolibs.machines.power.ManualGenBlockRenderer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenGui;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.capability.germ.GermEventsHandler;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.ONIConstants;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ONIClientEventsHandler {

    @SubscribeEvent
    public static void clientPreInit(FMLClientSetupEvent event) {
        //TESRs
        if (ModList.get().isLoaded("geckolib3")) {
            ClientRegistry.bindTileEntityRenderer(ONIBlocks.COAL_GEN_TE.get(), t -> new GeckolibBlockRendererBase<CoalGenTE>(t, ONIConstants.Geo.COAL_GEN_TE));
            ClientRegistry.bindTileEntityRenderer(ONIBlocks.MANUAL_GEN_TE.get(), ManualGenBlockRenderer::new);
        }

        //Keybindings
        ONIKeybinds.init();

        //Events
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::itemToolTipEvent);

        //GUI Attachments
        ScreenManager.registerFactory(ONIBlocks.COAL_GEN_CONTAINER.get(), CoalGenGui::new);
    }
}
