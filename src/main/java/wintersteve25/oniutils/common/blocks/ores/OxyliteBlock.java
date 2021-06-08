package wintersteve25.oniutils.common.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.blocks.libs.IGasEmissionBlock;

public class OxyliteBlock extends Block implements IGasEmissionBlock {
    private static final String regName = "Oxylite";

    public OxyliteBlock() {
        super(Properties.of(Material.STONE).harvestLevel(1).strength(4, 10).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE));
    }

    public String getRegName() {
        return regName;
    }
}
