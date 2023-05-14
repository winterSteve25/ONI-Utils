package com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface IPlacementCondition extends BiPredicate<BlockPlaceContext, BlockState> {
}
