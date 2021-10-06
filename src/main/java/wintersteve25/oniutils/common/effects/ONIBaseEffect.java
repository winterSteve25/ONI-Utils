package wintersteve25.oniutils.common.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import wintersteve25.oniutils.common.init.ONIEffects;

public class ONIBaseEffect extends Effect {
    private final String regName;

    public ONIBaseEffect(EffectType effectType, int color, String regName) {
        super(effectType, color);
        this.regName = regName;
    }

    public String getRegName() {
        return regName;
    }

    public void initEffect(ONIBaseEffect e) {
        ONIEffects.effectList.add(e);
    }
}
