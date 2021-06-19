package wintersteve25.oniutils.common.blocks.ores.slime;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;

public class SlimeBlock extends ONIBaseRock {
    private static final String regName = "Slime";

    public SlimeBlock() {
        super(0, 0.25f, 2, regName, SoundType.SLIME_BLOCK, Material.DIRT);
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.SlimeTE.get().create();
    }

}
