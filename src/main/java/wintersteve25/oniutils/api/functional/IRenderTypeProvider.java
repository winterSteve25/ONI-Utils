package wintersteve25.oniutils.api.functional;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;

@FunctionalInterface
public interface IRenderTypeProvider {
    BlockRenderType createRenderType(BlockState state);
}
