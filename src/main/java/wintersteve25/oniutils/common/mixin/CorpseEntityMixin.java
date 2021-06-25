package wintersteve25.oniutils.common.mixin;

import de.maxhenkel.corpse.corelib.death.Death;
import de.maxhenkel.corpse.entities.CorpseEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import wintersteve25.oniutils.common.capability.germ.GermCapability;

@Mixin(CorpseEntity.class)
public class CorpseEntityMixin{

    /**
     * @author Mixin by winterSteve25, original method by henkelmax
     */

    @Overwrite()
    public static CorpseEntity createFromDeath(PlayerEntity player, Death death) {
        CorpseEntity corpseMixin = new CorpseEntity(player.level);
        corpseMixin.setDeath(death);
        corpseMixin.setCorpseUUID(death.getPlayerUUID());
        corpseMixin.setCorpseName(death.getPlayerName());
        corpseMixin.setEquipment(death.getEquipment());
        corpseMixin.setPos(death.getPosX(), Math.max(death.getPosY(), 0.0D), death.getPosZ());
        corpseMixin.yRot = player.yRot;
        corpseMixin.setCorpseModel(death.getModel());
        corpseMixin.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(c ->  {
            player.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(p -> {
                c.setGerm(p.getGermType(), p.getGermAmount());
            });
        });
        return corpseMixin;
    }
}
