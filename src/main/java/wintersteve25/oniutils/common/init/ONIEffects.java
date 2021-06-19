package wintersteve25.oniutils.common.init;

import wintersteve25.oniutils.common.effects.ONIBaseEffect;
import wintersteve25.oniutils.common.effects.OxygenatedEffect;

import java.util.ArrayList;
import java.util.List;

public class ONIEffects {

    public static final OxygenatedEffect OXYGENATED = new OxygenatedEffect();

    public static List<ONIBaseEffect> effectList = new ArrayList<>();

    static {
        OXYGENATED.initEffect(OXYGENATED);
    }

    public static void register() {}
}
