package wintersteve25.oniutils.common.capability.durability;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import wintersteve25.oniutils.ONIUtils;

public class DurabilityEventsHandler {
    public static void entityCapAttachEvent (AttachCapabilitiesEvent<TileEntity> event) {
        TileEntity te = event.getObject();
        if (te != null) {
            if (!te.getCapability(DurabilityCapability.DURABILITY_CAPABILITY).isPresent()) {
                DurabilityCapabilityProvider provider = new DurabilityCapabilityProvider();
                event.addCapability(new ResourceLocation(ONIUtils.MODID, "oniDurability"), provider);
                event.addListener(provider::invalidate);
            }
        }
    }
}
