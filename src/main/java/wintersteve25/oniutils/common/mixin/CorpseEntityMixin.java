//package wintersteve25.oniutils.common.mixin;
//
//import de.maxhenkel.corpse.corelib.death.Death;
//import de.maxhenkel.corpse.entities.CorpseEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//import wintersteve25.oniutils.common.capability.germ.GermsCapability;
//
//@Mixin(CorpseEntity.class)
//public class CorpseEntityMixin{
//    @Inject(method = "createFromDeath", at = @At(value = "RETURN", target = "Lde/maxhenkel/corpse/entities/CorpseEntity;createFromDeath(Lnet/minecraft/entity/player/PlayerEntity;Lde/maxhenkel/corpse/corelib/death/Death;)Lde/maxhenkel/corpse/entities/CorpseEntity;"))
//    private static void createFromDeath(PlayerEntity player, Death death, CallbackInfoReturnable<CorpseEntity> cir) {
//        CorpseEntity corpseMixin = new CorpseEntity(player.level);
//        corpseMixin.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(c ->  {
//            player.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(p -> {
//                c.setGerm(p.getGermType(), p.getGermAmount());
//            });
//        });
//    }
//}
