package wintersteve25.oniutils.common.blocks.ores.oxylite;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;

import javax.annotation.Nullable;

public class OxyliteBlock extends ONIBaseRock implements ITileEntityProvider {
    private static final String regName = "Oxylite";

    public OxyliteBlock() {
        super(1, 4, 10, regName);
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return new OxyliteTile();
    }
}
