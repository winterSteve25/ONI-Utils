package wintersteve25.oniutils.common.blocks.libs;

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

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        super(Properties.of(Material.STONE).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).strength(hardness, resistance));
        this.regName = regName;
    }

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(Properties.of(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).strength(hardness, resistance));
        this.regName = regName;
    }

    public ONIBaseRock(String regName, Properties properties) {
        super(properties);
        this.regName = regName;
    }

    public String getRegName() {
        return this.regName;
    }

    public void initRock(ONIBaseRock b) {
        ONIBlocks.rocksList.add(b);
    }

    public void initRockNoDataGen(ONIBaseRock b) {
        ONIBlocks.rocksListNoDataGen.add(b);
    }
}
