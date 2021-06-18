package wintersteve25.oniutils.common.blocks.libs;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ONIBaseMachine extends ONIBaseDirectional {

    public ONIBaseMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(harvestLevel, hardness, resistance, regName, soundType, material);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
