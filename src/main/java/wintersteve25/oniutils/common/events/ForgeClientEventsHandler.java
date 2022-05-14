package wintersteve25.oniutils.common.events;

import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TriggerPlayerMovePacket;
import wintersteve25.oniutils.common.registration.PlayerMovingEvent;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEventsHandler {
    @SubscribeEvent
    public static void userInput(MovementInputUpdateEvent event) {
        PlayerMovingEvent.MovementTypes type = null;

        Input input = event.getInput();

        if (input.down) {
            type = PlayerMovingEvent.MovementTypes.S;
        } else if (input.up) {
            type = PlayerMovingEvent.MovementTypes.W;
        } else if (input.jumping) {
            type = PlayerMovingEvent.MovementTypes.JUMP;
        } else if (input.left) {
            type = PlayerMovingEvent.MovementTypes.A;
        } else if (input.right) {
            type = PlayerMovingEvent.MovementTypes.D;
        } else if (input.shiftKeyDown) {
            type = PlayerMovingEvent.MovementTypes.SNEAK;
        }

        if (type == null) return;

        ONINetworking.sendToServer(new TriggerPlayerMovePacket(event.getPlayer().getUUID(), type));
    }
}
