package wintersteve25.oniutils.api.functional;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface IPlacementCondition extends BiPredicate<BlockItemUseContext, BlockState> {
}
