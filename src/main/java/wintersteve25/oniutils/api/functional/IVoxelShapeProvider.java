package wintersteve25.oniutils.api.functional;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

@FunctionalInterface
public interface IVoxelShapeProvider {
    VoxelShape createShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);
}
