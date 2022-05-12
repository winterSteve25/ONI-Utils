package wintersteve25.oniutils.common.capability.circuits;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import wintersteve25.oniutils.ONIUtils;

public class CircuitEventsHandler {
    public static void worldCapAttachEvent(AttachCapabilitiesEvent<World> event) {
        World world = event.getObject();
        if (!world.isRemote()) {
            if (!world.getCapability(CircuitCapability.WORLD_CIRCUIT_CAPABILITY).isPresent()) {
                CircuitCapProv provider = new CircuitCapProv();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "circuits"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }
}
