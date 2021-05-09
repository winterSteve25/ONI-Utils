package wintersteve25.oniutils.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIBaseRock extends Block {
    private final String regName;

    public ONIBaseRock(int harvestLevel, float hardness, float resistance) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(hardness, resistance));
        this.regName = null;
    }

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).strength(hardness, resistance));
        this.regName = regName;
    }

    public String getRegName() {
        return this.regName;
    }

    public void initBlock(ONIBaseRock b) {
        ONIBlocks.rocksList.add(b);
    }
}
