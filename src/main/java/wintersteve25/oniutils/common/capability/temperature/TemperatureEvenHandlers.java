package wintersteve25.oniutils.common.capability.temperature;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.capability.temperature.api.ICustomTemperatureProvider;

import java.util.List;

public class TemperatureEvenHandlers {
    public static void chunkAttach(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            if (!(chunk instanceof ICustomTemperatureProvider)) {
                if (!chunk.getCapability(TemperatureCapability.CAPABILITY_TEMPERATURE).isPresent()) {
                    TemperatureCapabilityProvider provider = new TemperatureCapabilityProvider();
                    event.addCapability(new ResourceLocation(ONIUtils.MODID, "temperature"), provider);
                    event.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void entityAttach(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity != null) {
            if (!(entity instanceof ICustomTemperatureProvider)) {
                if (!entity.getCapability(TemperatureCapability.CAPABILITY_TEMPERATURE).isPresent()) {
                    TemperatureCapabilityProvider provider = new TemperatureCapabilityProvider();
                    event.addCapability(new ResourceLocation(ONIUtils.MODID, "temperature"), provider);
                    event.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void teAttach(AttachCapabilitiesEvent<TileEntity> event) {
        TileEntity te = event.getObject();
        if (te != null) {
            if (!(te instanceof ICustomTemperatureProvider)) {
                if (!te.getCapability(TemperatureCapability.CAPABILITY_TEMPERATURE).isPresent()) {
                    TemperatureCapabilityProvider provider = new TemperatureCapabilityProvider();
                    event.addCapability(new ResourceLocation(ONIUtils.MODID, "temperature"), provider);
                    event.addListener(provider::invalidate);
                }
            }
        }
    }

    public static void tick(TickEvent.WorldTickEvent event) {
        if (!event.world.isClientSide()) {
            List<TileEntity> tiles = event.world.tickableBlockEntities;
            for (TileEntity te : tiles) {
                te.getCapability(TemperatureCapability.CAPABILITY_TEMPERATURE).ifPresent(t -> {
                    Chunk chunk = event.world.getChunkAt(te.getBlockPos());
                    chunk.getCapability(TemperatureCapability.CAPABILITY_TEMPERATURE).ifPresent(c -> {
                        c.addTemperature(t.getTemperature()/1000);
                    });
                });
            }
        }
    }
}
