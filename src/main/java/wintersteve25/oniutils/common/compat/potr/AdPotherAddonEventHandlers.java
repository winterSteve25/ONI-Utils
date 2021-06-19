//package wintersteve25.oniutils.common.compat.potr;
//
//import com.endertech.minecraft.mods.adpother.AdPother;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.potion.EffectInstance;
//import net.minecraft.potion.Effects;
//import net.minecraftforge.event.TickEvent;
//import wintersteve25.oniutils.ONIUtils;
//import wintersteve25.oniutils.common.init.ONIConfig;
//import wintersteve25.oniutils.common.init.ONIEffects;
//
//public class AdPotherAddonEventHandlers {
//    private static int playerEmitSpeed = ONIConfig.PLAYER_EMIT_SPEED.get();
//    private static int playerBreathTimer = ONIConfig.PLAYER_BREATH_TIMER.get();
//
//    public static void playerTick(TickEvent.PlayerTickEvent event) {
//        PlayerEntity player = event.player;
//        if (player != null) {
//            if (!player.getCommandSenderWorld().isClientSide()) {
//                playerEmitSpeed--;
//                if (playerEmitSpeed < 0) {
//                    AdPother.getInstance().sources.playerDeath.emitFrom(player, 1.0F);
//                    playerEmitSpeed = ONIConfig.PLAYER_EMIT_SPEED.get();
//                }
//
//                if (!player.hasEffect(ONIEffects.OXYGENATED)) {
//                    playerBreathTimer--;
//                    if (playerBreathTimer < 80) {
//                        player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN));
//                        if (playerBreathTimer < 0) {
//                            player.hurt(ONIUtils.oxygenDamage,4);
//                        }
//                    }
//                }
//
//                if (player.hasEffect(ONIEffects.OXYGENATED)){
//                    playerBreathTimer = ONIConfig.PLAYER_BREATH_TIMER.get();
//                }
//            }
//        }
//    }
//}
