package wintersteve25.oniutils.common.blocks.ores.slime;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;

public class SlimeBlock extends ONIBaseRock {
    private static final String regName = "Slime";

    public SlimeBlock() {
        super(0, 0.25f, 2, regName, SoundType.SLIME_BLOCK, Material.DIRT);
    }

    @Override
    public String getRegName() {
        return regName;
    }
}
