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

    private static final VoxelShape NORTH = VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 32, 1, 16),
            Block.makeCuboidShape(2, 31, 1, 30, 32, 14),
            Block.makeCuboidShape(0, 3, 1, 2, 32, 14),
            Block.makeCuboidShape(30, 3, 1, 32, 32, 14),
            Block.makeCuboidShape(2, 3, 0, 30, 31, 1)).simplify();
    private static final VoxelShape SOUTH = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_180);
    private static final VoxelShape WEST = VoxelShapeUtils.rotate(NORTH, Rotation.COUNTERCLOCKWISE_90);
    private static final VoxelShape EAST = VoxelShapeUtils.rotate(NORTH, Rotation.CLOCKWISE_90);

    public ManualGenBlock() {
        super(Properties.create(Material.IRON).harvestLevel(0).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3, 3).notSolid().setRequiresTool(), regName, null, 0, ManualGenTE.class);
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

            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public String machineName() {
        return "oniutils.gui.titles.manual_gen";
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
