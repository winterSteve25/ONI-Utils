package wintersteve25.oniutils.common.contents.base.interfaces.functional;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface IRenderTypeProvider {
    RenderShape createRenderType(BlockState state);
}
