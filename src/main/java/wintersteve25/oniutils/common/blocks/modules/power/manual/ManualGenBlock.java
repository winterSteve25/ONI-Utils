package wintersteve25.oniutils.common.blocks.modules.power.manual;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseMachine;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class ManualGenBlock extends ONIBaseMachine{

    private static final String regName = "Manual Generator";

    public ManualGenBlock() {
        super(Properties.of(Material.HEAVY_METAL).harvestLevel(0).harvestTool(ToolType.PICKAXE).strength(3, 3).noOcclusion(), regName);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.MANUAL_GEN_TE.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isClientSide()) {
            if (player instanceof ServerPlayerEntity) {
                TileEntity tile = world.getBlockEntity(pos);
                if (tile instanceof ManualGenTE) {
                    ManualGenTE te = (ManualGenTE) tile;
                    ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;
                    te.getOnMill(playerEntity, pos, state);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.FAIL;
    }

    //    @Override
//    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
//        return NORTH;
//    }
//
//    @Override
//    public VoxelShape getOcclusionShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
//        return NORTH;
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
//        switch((Direction)p_220053_1_.getValue(FACING)) {
//            case SOUTH:
//                return SOUTH;
//            case EAST:
//                return EAST;
//            case WEST:
//                return WEST;
//            default:
//                return NORTH;
//        }
//    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
