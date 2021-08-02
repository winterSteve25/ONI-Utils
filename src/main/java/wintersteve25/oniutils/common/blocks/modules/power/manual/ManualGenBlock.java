package wintersteve25.oniutils.common.blocks.modules.power.manual;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
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
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.blocks.base.ONIBaseMachineAnimated;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenContainer;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class ManualGenBlock extends ONIBaseMachineAnimated implements ONIIHasGeoItem {

    private static final String regName = "Manual Generator";

//    private static final VoxelShape LEG_1 = Block.makeCuboidShape(3D, 0, 14D, 9D, 2D, 2D);
//    private static final VoxelShape LEG_2 = Block.makeCuboidShape(16+7D, 0, 14D, 32-3D, 2D, 2D);
//    private static final VoxelShape FRAME_1_STRAIGHT = Block.makeCuboidShape(7D, 2D, 13D, 32-8D, 3D, 3D);
//    private static final VoxelShape FRAME_2_STRAIGHT = Block.makeCuboidShape(1D, 10D, 13D, 2D, 9+16D, 3D);
//    private static final VoxelShape FRAME_3_STRAIGHT = Block.makeCuboidShape(32-2D, 9D, 13D, 32-1D, 9+16D, 3D);
//    private static final VoxelShape FRAME_4_STRAIGHT = Block.makeCuboidShape(8D, 32-2D, 13D, 32-7D, 32-1D, 3D);
//    private static final VoxelShape FRAME_STRAIGHT = VoxelShapes.or(FRAME_1_STRAIGHT, FRAME_2_STRAIGHT, FRAME_3_STRAIGHT, FRAME_4_STRAIGHT);
//    private static final VoxelShape FRAME_1_TILTED = Block.makeCuboidShape(2D, 9D, 13D, 8D, 3D, 3D).;
//    private static final VoxelShape FRAME_TILTED = VoxelShapes.or(FRAME_1_TILTED);
//
//    private static final VoxelShape NORTH = VoxelShapes.or(LEG_1, LEG_2, FRAME_STRAIGHT).simplify();
//    private static final VoxelShape SOUTH = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_180);
//    private static final VoxelShape WEST = VoxelShapeUtils.rotate(NORTH, Rotation.COUNTERCLOCKWISE_90);
//    private static final VoxelShape EAST = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_90);

    private static final VoxelShape NORTH = VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 32, 1, 16),
            Block.makeCuboidShape(2, 31, 1, 30, 32, 14),
            Block.makeCuboidShape(0, 3, 1, 2, 32, 14),
            Block.makeCuboidShape(30, 3, 1, 32, 32, 14),
            Block.makeCuboidShape(2, 3, 0, 30, 31, 1)).simplify();
    private static final VoxelShape SOUTH = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_180);
    private static final VoxelShape WEST = VoxelShapeUtils.rotate(NORTH, Rotation.COUNTERCLOCKWISE_90);
    private static final VoxelShape EAST = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_90);

    public ManualGenBlock() {
        super(Properties.create(Material.IRON).harvestLevel(0).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3, 3).notSolid().setRequiresTool(), regName, null, 0);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.MANUAL_GEN_TE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ManualGenTE) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("oniutils.gui.machines.manual_gen");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ManualGenContainer(i, world, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                ONIUtils.LOGGER.warn("Wrong tileEntity type found, failed to create container");
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
