package wintersteve25.oniutils.common.utils.registration;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.geckolib.machines.power.coal.CoalGenGeckoRenderer;
import wintersteve25.oniutils.client.geckolib.machines.power.manual.ManualGenGeckoRenderer;
import wintersteve25.oniutils.client.keybinds.ONIKeybinds;
import wintersteve25.oniutils.common.blocks.modules.power.coalgen.CoalGenGui;
import wintersteve25.oniutils.common.capability.germ.GermEventsHandler;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.trait.TraitCapability;
import wintersteve25.oniutils.common.capability.trait.TraitEventsHandler;
//import wintersteve25.oniutils.common.compat.potr.AdPotherAddonEventHandlers;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIConfig;

public class ONIGeneralEventHandlers {

    public static void preInit(FMLCommonSetupEvent evt) {
        GermsCapability.register();
        TraitCapability.register();
        PlasmaCapability.register();

        //germ events
        if (ONIConfig.ENABLE_GERMS.get()) {
            MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GermEventsHandler::entityCapAttachEvent);
            MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, GermEventsHandler::teCapAttachEvent);
            MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, GermEventsHandler::itemCapAttachEvent);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnInteractEntitySpecific);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnInteractEntity);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnPickItem);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTossItem);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTileInteract);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::playerTickEvent);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::keepGermWhilePlaced);
        }

        //gas
        if (ONIConfig.ENABLE_GAS.get()) {
            //MinecraftForge.EVENT_BUS.addListener(AdPotherAddonEventHandlers::playerTick);
        }

        //traits
        if (ONIConfig.ENABLE_TRAITS.get()) {
            MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, TraitEventsHandler::entityCapAttachEvent);
            MinecraftForge.EVENT_BUS.addListener(TraitEventsHandler::onPlayerCloned);
            MinecraftForge.EVENT_BUS.addListener(TraitEventsHandler::onPlayerLoggedIn);
            MinecraftForge.EVENT_BUS.addListener(TraitEventsHandler::playerTickEvent);
        }
    }

    @SuppressWarnings("unchecked")
    @OnlyIn(Dist.CLIENT)
    public static void clientPreInit(FMLClientSetupEvent event) {
        //TESRs
        ClientRegistry.bindTileEntityRenderer(ONIBlocks.COAL_GEN_TE.get(), CoalGenGeckoRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ONIBlocks.MANUAL_GEN_TE.get(), ManualGenGeckoRenderer::new);

        //Keybindings
        ONIKeybinds.init();

        //Events
        MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::itemToolTipEvent);

        //GUI Attachments
        ScreenManager.register(ONIBlocks.COAL_GEN_CONTAINER.get(), CoalGenGui::new);
    }

}
