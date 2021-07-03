package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ONIBaseAnimatedMachine extends ONIBaseDirectional {


    public ONIBaseAnimatedMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(harvestLevel, hardness, resistance, regName, soundType, material);
    }

    public ONIBaseAnimatedMachine(Properties properties, String regName) {
        super(properties, regName);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
