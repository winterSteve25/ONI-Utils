package wintersteve25.oniutils.common.contents.base;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import wintersteve25.oniutils.common.init.ONIEffects;

public class ONIBaseEffect extends MobEffect {
    private final String regName;

    public ONIBaseEffect(MobEffectCategory effectType, int color, String regName) {
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