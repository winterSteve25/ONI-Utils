package wintersteve25.oniutils.common.blocks.base.bounding;

import mekanism.common.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.particle.DiggingParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootParameters;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Modified from https://github.com/mekanism/Mekanism/blob/1.16.x/src/main/java/mekanism/common/block/BlockBounding.java
 * Compatible with MIT License https://github.com/mekanism/Mekanism/blob/1.16.x/LICENSE
 */

@SuppressWarnings("deprecation")
public class ONIBoundingBlock extends ONIBaseBlock {

    private boolean canOutputRedstone = false;

    @Nullable
    public static BlockPos getMainBlockPos(IBlockReader world, BlockPos thisPos) {
        ONIBoundingTE te = (ONIBoundingTE) WorldUtils.getTileEntity(ONIBoundingTE.class, world, thisPos);
        return te != null && te.receivedCoords && !thisPos.equals(te.getMainPos()) ? te.getMainPos() : null;
    }

    public ONIBoundingBlock() {
        this("Bounding Block", Properties.create(Material.IRON).hardnessAndResistance(3.5F, 4.8F).setRequiresTool().variableOpacity().notSolid());
    }

    public ONIBoundingBlock(String regName, Properties properties) {
        super(regName, properties);
    }

    @Nonnull
    @Deprecated
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return ActionResultType.FAIL;
        } else {
            BlockState state1 = world.getBlockState(mainPos);
            return state1.getBlock().onBlockActivated(state1, world, mainPos, player, hand, hit);
        }
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            BlockPos mainPos = getMainBlockPos(world, pos);
            if (mainPos != null) {
                BlockState mainState = world.getBlockState(mainPos);
                if (!mainState.isAir(world, mainPos)) {
                    world.removeBlock(mainPos, false);
                }
            }

            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull BlockState state, RayTraceResult target, @Nonnull IBlockReader world, @Nonnull BlockPos pos, PlayerEntity player) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return ItemStack.EMPTY;
        } else {
            BlockState state1 = world.getBlockState(mainPos);
            return state1.getBlock().getPickBlock(state1, target, world, mainPos, player);
        }
    }

    @Override
    public boolean removedByPlayer(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, boolean willHarvest, FluidState fluidState) {
        if (willHarvest) {
            return true;
        } else {
            BlockPos mainPos = getMainBlockPos(world, pos);
            if (mainPos != null) {
                BlockState mainState = world.getBlockState(mainPos);
                if (!mainState.isAir(world, mainPos)) {
                    mainState.removedByPlayer(world, mainPos, player, false, mainState.getFluidState());
                }
            }

            return super.removedByPlayer(state, world, pos, player, false, fluidState);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            BlockState mainState = world.getBlockState(mainPos);
            if (!mainState.isAir(world, mainPos)) {
                net.minecraft.loot.LootContext.Builder lootContextBuilder = (new net.minecraft.loot.LootContext.Builder((ServerWorld) world)).withRandom(world.rand).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(mainPos)).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, mainState.hasTileEntity() ? WorldUtils.getTileEntity(world, mainPos) : null).withNullableParameter(LootParameters.THIS_ENTITY, explosion.getExploder());
                if (explosion.mode == Explosion.Mode.DESTROY) {
                    lootContextBuilder.withParameter(LootParameters.EXPLOSION_RADIUS, explosion.size);
                }

                mainState.getDrops(lootContextBuilder).forEach((stack) -> {
                    Block.spawnAsEntity(world, mainPos, stack);
                });
                mainState.onBlockExploded(world, mainPos, explosion);
            }
        }

        super.onBlockExploded(state, world, pos, explosion);
    }

    @Override
    public void harvestBlock(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull BlockPos pos, @Nonnull BlockState state, TileEntity te, @Nonnull ItemStack stack) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            BlockState mainState = world.getBlockState(mainPos);
            mainState.getBlock().harvestBlock(world, player, mainPos, mainState, WorldUtils.getTileEntity(world, mainPos), stack);
        } else {
            super.harvestBlock(world, player, pos, state, te, stack);
        }

        world.removeBlock(pos, false);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean isMoving) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos != null) {
            world.getBlockState(mainPos).neighborChanged(world, mainPos, neighborBlock, neighborPos, isMoving);
        }
    }

    @Override
    public float getPlayerRelativeBlockHardness(@Nonnull BlockState state, @Nonnull PlayerEntity player, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        return mainPos == null ? super.getPlayerRelativeBlockHardness(state, player, world, pos) : world.getBlockState(mainPos).getPlayerRelativeBlockHardness(player, world, mainPos);
    }

    @Override
    public float getExplosionResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        return mainPos == null ? super.getExplosionResistance(state, world, pos, explosion) : world.getBlockState(mainPos).getExplosionResistance(world, mainPos, explosion);
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(@Nonnull BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return ONIBlocks.BOUNDING_TE.get().create();
    }

    @Nonnull
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        BlockPos mainPos = getMainBlockPos(world, pos);
        if (mainPos == null) {
            return VoxelShapes.empty();
        } else {
            BlockState mainState;
            try {
                mainState = (world).getBlockState(mainPos);
            } catch (ArrayIndexOutOfBoundsException var9) {
                if (!(world instanceof ChunkRenderCache)) {
                    ONIUtils.LOGGER.error("Error getting bounding block shape, for position {}, with main position {}. World of type {}", pos, mainPos, world.getClass().getName());
                    return VoxelShapes.empty();
                }

                world = ((ChunkRenderCache) world).world;
                mainState = (world).getBlockState(mainPos);
            }

            VoxelShape shape = mainState.getShape(world, mainPos, context);
            BlockPos offset = pos.subtract(mainPos);
            return shape.withOffset((double) (-offset.getX()), (double) (-offset.getY()), (double) (-offset.getZ()));
        }
    }

    //    @Nonnull
//    @Override
//    public FluidState getFluidState(@Nonnull BlockState state) {
//        return this.getFluid(state);
//    }
//
//    @Nonnull
//    @Override
//    public BlockState updatePostPlacement(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld world, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
//        this.updateFluids(state, world, currentPos);
//        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
//    }

    @Override
    public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull PathType type) {
        return false;
    }

    @Override
    public boolean addHitEffects(BlockState state, World world, RayTraceResult target, ParticleManager manager) {
        if (target.getType() == RayTraceResult.Type.BLOCK && target instanceof BlockRayTraceResult) {
            BlockRayTraceResult blockTarget = (BlockRayTraceResult) target;
            BlockPos pos = blockTarget.getPos();
            BlockPos mainPos = getMainBlockPos(world, pos);
            if (mainPos != null) {
                BlockState mainState = world.getBlockState(mainPos);
                if (!mainState.isAir(world, mainPos)) {
                    AxisAlignedBB axisalignedbb = state.getShape(world, pos).getBoundingBox();
                    double x = (double) pos.getX() + world.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.2D) + 0.1D + axisalignedbb.minX;
                    double y = (double) pos.getY() + world.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.2D) + 0.1D + axisalignedbb.minY;
                    double z = (double) pos.getZ() + world.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.2D) + 0.1D + axisalignedbb.minZ;
                    Direction side = blockTarget.getFace();
                    if (side == Direction.DOWN) {
                        y = (double) pos.getY() + axisalignedbb.minY - 0.1D;
                    } else if (side == Direction.UP) {
                        y = (double) pos.getY() + axisalignedbb.maxY + 0.1D;
                    } else if (side == Direction.NORTH) {
                        z = (double) pos.getZ() + axisalignedbb.minZ - 0.1D;
                    } else if (side == Direction.SOUTH) {
                        z = (double) pos.getZ() + axisalignedbb.maxZ + 0.1D;
                    } else if (side == Direction.WEST) {
                        x = (double) pos.getX() + axisalignedbb.minX - 0.1D;
                    } else if (side == Direction.EAST) {
                        x = (double) pos.getX() + axisalignedbb.maxX + 0.1D;
                    }

                    manager.addEffect((new DiggingParticle((ClientWorld) world, x, y, z, 0.0D, 0.0D, 0.0D, mainState)).setBlockPos(mainPos).multiplyVelocity(0.2F).multiplyParticleScaleBy(0.6F));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
    }
}
