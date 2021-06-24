package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import wintersteve25.oniutils.common.blocks.base.ONIBaseMachine;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helper.ISHandlerHelper;

import javax.annotation.Nullable;

import static wintersteve25.oniutils.common.utils.helper.MiscHelper.ONEPIXEL;

@SuppressWarnings("deprecation")
public class CoalGenBlock extends ONIBaseMachine {

    private static final String regName = "Coal Generator";
    private static final VoxelShape BOTTOM1 = VoxelShapes.box(0D, (ONEPIXEL/16)*2, 0D, 1D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM2 = VoxelShapes.box(1D, (ONEPIXEL/16)*2, 0D, 2D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM = VoxelShapes.or(BOTTOM1, BOTTOM2);
    private static final VoxelShape SUPPORT1 = VoxelShapes.box(ONEPIXEL*4, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, ONEPIXEL*6, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT2 = VoxelShapes.box(2D-ONEPIXEL*1, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, 2D-ONEPIXEL*3, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT = VoxelShapes.or(SUPPORT1, SUPPORT2);
    private static final VoxelShape MIDDLE = VoxelShapes.box(ONEPIXEL*6, ONEPIXEL*6, ONEPIXEL*4, 2D-ONEPIXEL*3, 1+ONEPIXEL*9, 1D-ONEPIXEL*4);
    private static final VoxelShape NORTH = VoxelShapes.or(BOTTOM, SUPPORT, MIDDLE);

//    private static final VoxelShape SOUTH = VoxelShapes.or(Block.box(0D, ONEPIXEL*2, 0D, -32D, 1D + ONEPIXEL*2, -16D), VoxelShapes.or(Block.box(-4D, 1D + ONEPIXEL*2, -5D, -6D, 26D, -11D), Block.box(-32D+5D, 1D + ONEPIXEL*2, -6D, -32D+1D, 26D, -11D)), Block.box(-6D, 6D, -4D, -32+3D, -16-9D, -16+4D));
//    private static final VoxelShape EAST = VoxelShapes.or(BOTTOM, SUPPORT, MIDDLE);
//    private static final VoxelShape WEST = VoxelShapes.or(BOTTOM, SUPPORT, MIDDLE);

    public CoalGenBlock() {
        super(Properties.of(Material.HEAVY_METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).strength(3, 3).requiresCorrectToolForDrops(), regName);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isClientSide()) {
            TileEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof CoalGenTE) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new CoalGenContainer(i, world, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_196243_5_) {
        if (world.getBlockEntity(pos) instanceof CoalGenTE) {
            CoalGenTE te = (CoalGenTE) world.getBlockEntity(pos);
            if (te != null) {
                if (te.hasItem()) {
                    ISHandlerHelper.dropInventory(te, world, state, pos, te.getInvSize());
                }
            }
        }
        super.onRemove(state, world, pos, state2, p_196243_5_);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.COAL_GEN_TE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return NORTH;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
        return NORTH;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch((Direction)p_220053_1_.getValue(FACING)) {
//            case SOUTH:
//                return SOUTH;
//            case EAST:
//                return EAST;
//            case WEST:
//                return WEST;
            default:
                return NORTH;
        }
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
