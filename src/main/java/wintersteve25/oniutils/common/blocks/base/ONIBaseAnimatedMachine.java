package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.client.model.generators.ModelFile;

public class ONIBaseAnimatedMachine extends ONIBaseDirectional {

    public ONIBaseAnimatedMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, ModelFile modelFile) {
        super(harvestLevel, hardness, resistance, regName, soundType, material, modelFile);
    }

    public ONIBaseAnimatedMachine(Properties properties, String regName, ModelFile modelFile) {
        super(properties, regName, modelFile);
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
