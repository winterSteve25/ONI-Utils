package wintersteve25.oniutils.common.blocks.machines.power.coalgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseMachine;

import javax.annotation.Nullable;

public class CoalGenBlock extends ONIBaseMachine {

    public static String regName = "Coal Generator";
//    public static VoxelShape COMMON = VoxelShapes.box();

    public CoalGenBlock() {
        super(1, 5, 5, regName, SoundType.METAL, Material.HEAVY_METAL);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CoalGenTE();
    }

    @Override
    public String getRegName() {
        return regName;
    }
}
