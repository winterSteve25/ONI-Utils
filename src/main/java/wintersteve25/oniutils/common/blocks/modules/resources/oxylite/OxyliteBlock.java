package wintersteve25.oniutils.common.blocks.modules.resources.oxylite;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;

public class OxyliteBlock extends ONIBaseRock{
    private static final String regName = "Oxylite";

    public OxyliteBlock() {
        super(1, 4, 10, regName);
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
        return ONIBlocks.OxyliteTE.get().create();
    }
}
