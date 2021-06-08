package wintersteve25.oniutils.common.chunk.germ;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.chunk.germ.api.EnumGermTypes;

public class GermEventsHandler {
    public static void entityCapAttachEvent (AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof LivingEntity) {
            GermCapabilityProvider provider = new GermCapabilityProvider();

            e.addCapability(new ResourceLocation(ONIUtils.MODID, "germ_stack"), provider);
            e.addListener(provider::invalidate);
        }
    }

    public static void chunkCapAttachEvent (AttachCapabilitiesEvent<Chunk> e) {
        Chunk chunkAttached = e.getObject();
        if (chunkAttached != null) {
            GermCapabilityProvider provider = new GermCapabilityProvider();

            e.addCapability(new ResourceLocation(ONIUtils.MODID, "germ_stack"), provider);
            e.addListener(provider::invalidate);

            chunkAttached.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(chunk -> {
                chunk.addGerm(EnumGermTypes.SLIMELUNG, 1000);
            });
        }
    }

    public static void infectOnInteractEvent (PlayerInteractEvent e) {
        PlayerEntity user = e.getPlayer();
        if (user != null) {

            //if player has germ, infect to chunk
            user.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(player -> {
                BlockPos targetBlockPos = e.getPos();
                Chunk targetChunk = (Chunk) e.getWorld().getChunk(targetBlockPos);

                if (player.getGermType() != null && player.getGermAmount() > 0) {
                    targetChunk.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(chunk -> {
                        chunk.addGerm(player.getGermType(), player.getGermAmount());
                        e.setCanceled(true);
                        user.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectChunk", Integer.toString(player.getGermAmount()), player.getGermType().getName().replace('_', ' ')), true);
                    });
                }
            });

            //if chunk has germ, infect to player
            BlockPos targetBlockPos = e.getPos();
            Chunk targetChunk = (Chunk) e.getWorld().getChunk(targetBlockPos);

            targetChunk.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(chunk -> {
                if (chunk.getGermType() != null && chunk.getGermAmount() > 0) {
                    user.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(player -> {
                        player.addGerm(chunk.getGermType(), chunk.getGermAmount());
                        e.setCanceled(true);
                        user.displayClientMessage(new TranslationTextComponent("oniutils.message.germs.infectPlayer", Integer.toString(chunk.getGermAmount()), chunk.getGermType().getName().replace('_', ' ')), true);
                    });
                }
            });
        }
    }
}
