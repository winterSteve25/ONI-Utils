package wintersteve25.oniutils.common.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.germ.GermEventsHandler;
import wintersteve25.oniutils.common.capability.germ.GermCapability;
import wintersteve25.oniutils.common.capability.oni_te_data.ONITEDataCapability;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;
import wintersteve25.oniutils.common.capability.oni_player_data.ONIPlayerDataCapability;
import wintersteve25.oniutils.common.capability.oni_player_data.ONIPlayerDataEventsHandler;
import wintersteve25.oniutils.common.capability.world_gas.WorldGasCapability;
import wintersteve25.oniutils.common.capability.world_gas.WorldGasEventsHandler;
import wintersteve25.oniutils.common.commands.SetGermAmountCommands;
import wintersteve25.oniutils.common.init.ONIConfig;
import wintersteve25.oniutils.common.network.ONINetworking;

public class ONIServerEventsHandler {

    public static void commonSetup(FMLCommonSetupEvent evt) {
        ONINetworking.registerMessages();

        ONIUtils.LOGGER.info("Registering ONIUtils Capabilities");

        ONITEDataCapability.register();
        PlasmaCapability.register();
        WorldGasCapability.register();

        //germ events
        if (ONIConfig.ENABLE_GERMS.get()) {
            ONIUtils.LOGGER.info("Registering Germs Capability");
            GermCapability.register();
            MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GermEventsHandler::entityCapAttachEvent);
            MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, GermEventsHandler::teCapAttachEvent);
            MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, GermEventsHandler::itemCapAttachEvent);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnInteractEntitySpecific);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnPickItem);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTossItem);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::infectOnTileInteract);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::playerTickEvent);
            MinecraftForge.EVENT_BUS.addListener(GermEventsHandler::keepGermWhilePlaced);
        }

        //potr
//      if (ModList.get().isLoaded("adpother")) {
//        if (ONIConfig.ENABLE_GAS.get()) {
//            MinecraftForge.EVENT_BUS.addListener(AdPotherAddonEventHandlers::playerTick);
//        }
//      }

        //player data
        if (ModList.get().isLoaded("pmmo"))  {
            if (ONIConfig.ENABLE_TRAITS.get()) {
                ONIUtils.LOGGER.info("Registering Player Data");
                ONIPlayerDataCapability.register();

                MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ONIPlayerDataEventsHandler::entityCapAttachEvent);
                MinecraftForge.EVENT_BUS.addListener(ONIPlayerDataEventsHandler::onPlayerCloned);
                MinecraftForge.EVENT_BUS.addListener(ONIPlayerDataEventsHandler::onPlayerLoggedIn);
                MinecraftForge.EVENT_BUS.addListener(ONIPlayerDataEventsHandler::playerTickEvent);
                MinecraftForge.EVENT_BUS.addListener(ONIPlayerDataEventsHandler::playerMove);
            }
        }

        ONIUtils.LOGGER.info("Registering World Gas");
        MinecraftForge.EVENT_BUS.addGenericListener(Chunk.class, WorldGasEventsHandler::chunkCapAttachEvent);
        MinecraftForge.EVENT_BUS.addListener(WorldGasEventsHandler::worldTick);

        //Misc Event Listeners
    }

    public static void command(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        LiteralArgumentBuilder<CommandSource> requires = Commands.literal("oniutils").requires((commandSource) -> commandSource.hasPermissionLevel(3));
        LiteralCommandNode<CommandSource> source = dispatcher.register(requires.then(SetGermAmountCommands.register(dispatcher)));
        dispatcher.register(Commands.literal("veiledascent").redirect(source));

        ONIUtils.LOGGER.info("Registered ONIUtils Commands!");
    }
}
