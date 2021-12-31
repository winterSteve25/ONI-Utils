package wintersteve25.oniutils.common.events;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.keybinds.ONIKeybinds;
import wintersteve25.oniutils.client.renderers.entities.EmptyEntityRenderer;
import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibBlockRendererBase;
import wintersteve25.oniutils.common.capability.germ.GermEventsHandler;
import wintersteve25.oniutils.common.contents.modules.blocks.power.coal.CoalGenGui;
import wintersteve25.oniutils.common.contents.modules.blocks.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.contents.modules.blocks.power.manual.ManualGenEntity;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIEntities;
import wintersteve25.oniutils.common.utils.ONIConstants;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventsHandler {

    @SubscribeEvent
    public static void clientPreInit(FMLClientSetupEvent event) {
        //TESRs
        if (ModList.get().isLoaded("geckolib3")) {
            ClientRegistry.bindTileEntityRenderer(ONIBlocks.Machines.Power.COAL_GEN_TE.get(), t -> new GeckolibBlockRendererBase<CoalGenTE>(t, ONIConstants.Geo.COAL_GEN_TE));
        }

        //Keybindings
        ONIKeybinds.init();

        //Events
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::itemToolTipEvent);

        //GUI Attachments
        ScreenManager.registerFactory(ONIBlocks.Machines.Power.COAL_GEN_CONTAINER.get(), CoalGenGui::new);

        //Entities
    }

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation() == AtlasTexture.LOCATION_BLOCKS_TEXTURE) {
            event.addSprite(ONIConstants.Resources.CURIOS_GOGGLES);
        }
    }
}
