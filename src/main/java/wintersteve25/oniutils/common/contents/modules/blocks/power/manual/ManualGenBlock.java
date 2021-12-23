package wintersteve25.oniutils.common.contents.modules.blocks.power.manual;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.contents.base.ONIBaseMachine;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;

public class ManualGenBlock extends ONIBaseMachine implements ONIIHasGeoItem {

    private static final String regName = "Manual Generator";

    private static final VoxelShape NORTH = VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 32, 1, 16),
            Block.makeCuboidShape(2, 31, 1, 30, 32, 14),
            Block.makeCuboidShape(0, 3, 1, 2, 32, 14),
            Block.makeCuboidShape(30, 3, 1, 32, 32, 14),
            Block.makeCuboidShape(2, 3, 0, 30, 31, 1)).simplify();
    private static final VoxelShape SOUTH = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_180);
    private static final VoxelShape WEST = VoxelShapeUtils.rotate(NORTH, Rotation.COUNTERCLOCKWISE_90);
    private static final VoxelShape EAST = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_90);

    public ManualGenBlock() {
        super(regName, Properties.create(Material.IRON).harvestLevel(0).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3, 3).notSolid().setRequiresTool());
        setTeClass(ManualGenTE.class);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.Machines.Power.MANUAL_GEN_TE.get().create();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ManualGenTE) {

            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            default:
                return NORTH;
        }
    }
}
