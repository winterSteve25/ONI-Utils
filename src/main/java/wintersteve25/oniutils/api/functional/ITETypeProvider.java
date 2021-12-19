package wintersteve25.oniutils.api.functional;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

@FunctionalInterface
public interface ITETypeProvider {
    TileEntityType<? extends TileEntity> createTEType(BlockState state, IBlockReader world);
}
