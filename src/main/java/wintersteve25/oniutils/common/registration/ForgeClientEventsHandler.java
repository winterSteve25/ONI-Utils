package wintersteve25.oniutils.common.registration;

import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.network.ONINetworking;
import wintersteve25.oniutils.common.network.TriggerPlayerMovePacket;

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
