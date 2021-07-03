package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIBaseRock extends Block {

    private final String regName;

    public ONIBaseRock(int harvestLevel, float hardness, float resistance) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = null;
    }

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = regName;
    }

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = regName;
    }

    public ONIBaseRock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance).setRequiresTool());
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
