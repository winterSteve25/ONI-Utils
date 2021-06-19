package wintersteve25.oniutils.common.lib.helper;

import wintersteve25.oniutils.common.effects.ONIBaseEffect;
import wintersteve25.oniutils.common.init.ONIEffects;

public class ONIEffectsHelper {
    public static void register() {
        for (ONIBaseEffect e : ONIEffects.effectList) {
            RegistryHelper.registerEffects(MiscHelper.langToReg(e.getRegName()), () -> e);
        }
    }
}
