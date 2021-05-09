package wintersteve25.oniutils.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ONIBaseRock extends Block {
    public ONIBaseRock(int harvestLevel, float hardness, float resistance) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(hardness, resistance));
    }
}
