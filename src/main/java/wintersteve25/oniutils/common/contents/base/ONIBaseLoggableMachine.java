package wintersteve25.oniutils.common.contents.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * Dummy class for the builder
 */
public class ONIBaseLoggableMachine extends ONIBaseMachine {
    public ONIBaseLoggableMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(harvestLevel, hardness, resistance, regName, soundType, material);
    }

    public ONIBaseLoggableMachine(String regName, Properties properties) {
        super(regName, properties);
    }

    @Override
    public boolean isFluidLoggable() {
        return true;
    }
}
