package wintersteve25.oniutils.common.events.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class PlayerMovingEvent extends Event {
    private final MovementTypes movement;
    private final Player player;

    public PlayerMovingEvent(MovementTypes movement, Player player) {
        this.movement = movement;
        this.player = player;
    }

    public MovementTypes getMovement() {
        return movement;
    }

    public Player getPlayer() {
        return player;
    }

    public enum MovementTypes {
        JUMP,
        SNEAK,
        W,
        A,
        S,
        D
    }
}
