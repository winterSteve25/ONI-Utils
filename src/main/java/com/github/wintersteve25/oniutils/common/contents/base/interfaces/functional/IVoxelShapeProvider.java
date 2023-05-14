package com.github.wintersteve25.oniutils.common.contents.base.interfaces.functional;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

@FunctionalInterface
public interface IVoxelShapeProvider {
    VoxelShape createShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context);
}
