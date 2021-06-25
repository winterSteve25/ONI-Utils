package wintersteve25.oniutils.common.init;

import net.minecraft.potion.EffectType;
import wintersteve25.oniutils.common.effects.ONIBaseEffect;

import java.util.ArrayList;
import java.util.List;

public class ONIEffects {

    public static final ONIBaseEffect OXYGENATED = new ONIBaseEffect(EffectType.NEUTRAL, 29183, "Oxygenated");
    public static final ONIBaseEffect DEOXYGENATED = new ONIBaseEffect(EffectType.NEUTRAL, 7340032, "De-Oxygenated");

    public static List<ONIBaseEffect> effectList = new ArrayList<>();

    public static void register() {
        OXYGENATED.initEffect(OXYGENATED);
        DEOXYGENATED.initEffect(DEOXYGENATED);
    }
}
