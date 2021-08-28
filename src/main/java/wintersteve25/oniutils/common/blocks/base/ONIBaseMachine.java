package wintersteve25.oniutils.common.blocks.base;

import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.generators.ModelFile;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.capability.plasma.PlasmaCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class ONIBaseMachine extends ONIBaseDirectional {
    public ONIBaseMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, ModelFile modelFile, int angelOffset) {
        super(harvestLevel, hardness, resistance, regName, soundType, material, modelFile, angelOffset);
    }

    public ONIBaseMachine(String regName, Properties properties, ModelFile modelFile, int angelOffset) {
        super(regName, properties, modelFile, angelOffset);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

//    @Override
//    public void neighborChanged(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean isMoving) {
//        world.getBlockState(pos).neighborChanged(world, pos, neighborBlock, neighborPos, isMoving);
//    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {

//        BlockState state = super.getStateForPlacement(blockItemUseContext);
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());

//        if (state == null) {
//            return null;
//        }
//
//        state.with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//        state.with(FACING, blockItemUseContext.getNearestLookingDirection());

        return this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ONIBaseTE tile = WorldUtils.getTileEntity(ONIBaseTE.class, worldIn, pos);

        if (tile == null) {
            return;
        }

        if (tile instanceof IBoundingBlock) {
            ((IBoundingBlock) tile).onPlace();
        }

        if (tile.isInverted()) {
            tile.setForceStopped(!worldIn.isBlockPowered(pos));
        } else {
            tile.setForceStopped(worldIn.isBlockPowered(pos));
        }
    }

    @Override
    public void onReplaced(BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.hasTileEntity() && (!state.isIn(newState.getBlock()) || !newState.hasTileEntity())) {
            TileEntity tile = WorldUtils.getTileEntity(world, pos);
            if (tile instanceof IBoundingBlock) {
                ((IBoundingBlock) tile).onBreak(state);
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        if (state.getBlock() instanceof ONIIHasRedstoneOutput) {
            ONIIHasRedstoneOutput block = (ONIIHasRedstoneOutput) state.getBlock();
            AtomicBoolean isLowTrue = new AtomicBoolean(false);
            AtomicBoolean isHighTrue = new AtomicBoolean(false);

            block.te().getCapability(PlasmaCapability.POWER_CAPABILITY).ifPresent(power -> {
                if (power.getPower() / 100 < block.lowThreshold()) {
                    isLowTrue.set(true);
                }

                if (power.getPower() / 100 > block.highThreshold()) {
                    isHighTrue.set(true);
                }
            });

            return isHighTrue.get() || isLowTrue.get();
        }
        return false;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (this.canProvidePower(blockState)) {
            return 15;
        }
        return 0;
    }
}
