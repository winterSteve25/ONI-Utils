package wintersteve25.oniutils.common.events;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.modules.blocks.power.manual.ManualGenEntity;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TriggerPlayerMovePacket;
import wintersteve25.oniutils.common.registration.PlayerMovingEvent;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEventsHandler {
    @SubscribeEvent
    public static void userInput(InputUpdateEvent event) {
        PlayerMovingEvent.MovementTypes type = null;

        MovementInput input = event.getMovementInput();

        if (input.backKeyDown) {
            type = PlayerMovingEvent.MovementTypes.S;
        } else if (input.forwardKeyDown) {
            type = PlayerMovingEvent.MovementTypes.W;
        } else if (input.jump) {
            type = PlayerMovingEvent.MovementTypes.JUMP;
        } else if (input.leftKeyDown) {
            type = PlayerMovingEvent.MovementTypes.A;
        } else if (input.rightKeyDown) {
            type = PlayerMovingEvent.MovementTypes.D;
        } else if (input.sneaking) {
            type = PlayerMovingEvent.MovementTypes.SNEAK;
        }

        if (type == null) return;

        ONINetworking.sendToServer(new TriggerPlayerMovePacket(event.getPlayer().getUniqueID(), type));
    }
}
