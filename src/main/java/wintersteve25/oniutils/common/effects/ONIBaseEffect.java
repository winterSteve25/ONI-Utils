package wintersteve25.oniutils.common.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import wintersteve25.oniutils.common.init.ONIEffects;

public class ONIBaseEffect extends Effect {
    private final String regName;

    protected ONIBaseEffect(EffectType p_i50391_1_, int p_i50391_2_, String regName) {
        super(p_i50391_1_, p_i50391_2_);
        this.regName = regName;
    }

    public String getRegName() {
        return regName;
    }

    public void initEffect(ONIBaseEffect e) {
        ONIEffects.effectList.add(e);
    }
}
