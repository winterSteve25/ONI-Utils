package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.client.model.generators.ModelFile;

public abstract class ONIBaseMachineAnimated extends ONIBaseMachine {

    public ONIBaseMachineAnimated(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, ModelFile modelFile, int angelOffset) {
        super(harvestLevel, hardness, resistance, regName, soundType, material, modelFile, angelOffset);
    }

    public ONIBaseMachineAnimated(Properties properties, String regName, ModelFile modelFile, int angelOffset) {
        super(regName, properties, modelFile, angelOffset);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
