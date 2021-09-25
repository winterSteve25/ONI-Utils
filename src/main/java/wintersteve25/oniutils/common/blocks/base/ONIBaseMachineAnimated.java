package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.generators.ModelFile;

public abstract class ONIBaseMachineAnimated extends ONIBaseMachine {

    public ONIBaseMachineAnimated(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, ModelFile modelFile, int angelOffset, Class<? extends TileEntity> teClass) {
        super(harvestLevel, hardness, resistance, regName, soundType, material, modelFile, angelOffset, teClass);
    }

    public ONIBaseMachineAnimated(Properties properties, String regName, ModelFile modelFile, int angelOffset, Class<? extends TileEntity> teClass) {
        super(regName, properties, modelFile, angelOffset, teClass);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
