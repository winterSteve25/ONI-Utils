package wintersteve25.oniutils.common.blocks.modules.power.coal;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
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
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.ISHandlerHelper;
import wintersteve25.oniutils.common.utils.ModelFileHelper;

import javax.annotation.Nullable;

import static wintersteve25.oniutils.common.utils.MiscHelper.ONEPIXEL;

@SuppressWarnings("deprecation")
public class CoalGenBlock extends ONIBaseMachineAnimated implements ONIIHasGeoItem {

    private static final String regName = "Coal Generator";

    //TODO: Use Block#makeCuboidShape instead
    private static final VoxelShape BOTTOM1 = VoxelShapes.create(0D, 0, 0D, 1D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM2 = VoxelShapes.create(1D, 0, 0D, 2D, ONEPIXEL + (ONEPIXEL/16)*2, 1D);
    private static final VoxelShape BOTTOM = VoxelShapes.or(BOTTOM1, BOTTOM2);
    private static final VoxelShape SUPPORT1 = VoxelShapes.create(ONEPIXEL*6, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, ONEPIXEL*6, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT2 = VoxelShapes.create(2D-ONEPIXEL*1, ONEPIXEL + (ONEPIXEL/16)*2, ONEPIXEL*6, 2D-ONEPIXEL*3, 1+ONEPIXEL*10, ONEPIXEL*11);
    private static final VoxelShape SUPPORT = VoxelShapes.or(SUPPORT1, SUPPORT2);
    private static final VoxelShape MIDDLE = VoxelShapes.create(ONEPIXEL*6, ONEPIXEL*6, ONEPIXEL*4, 2D-ONEPIXEL*3, 1+ONEPIXEL*9, 1D-ONEPIXEL*4);
    private static final VoxelShape REDSTONEPANEL = VoxelShapeUtils.rotate(VoxelShapes.create(ONEPIXEL*4, ONEPIXEL, ONEPIXEL*14, ONEPIXEL*13, ONEPIXEL*13, 1D), Rotation.CLOCKWISE_90);
//    private static final VoxelShape REDSTONEPANEL = VoxelShapes.create(0, 0, 0, ONEPIXEL*2, 1D, 1D);
    private static final VoxelShape CONNECTION = VoxelShapeUtils.rotate(VoxelShapes.create(ONEPIXEL*7, ONEPIXEL*7, ONEPIXEL*12, ONEPIXEL*10, ONEPIXEL*11, ONEPIXEL*14), Rotation.CLOCKWISE_90);

    private static final VoxelShape NORTH_R = VoxelShapes.or(BOTTOM, SUPPORT, MIDDLE, CONNECTION, REDSTONEPANEL).simplify();
    private static final VoxelShape WEST_R = VoxelShapeUtils.rotate(NORTH_R, Rotation.COUNTERCLOCKWISE_90);
    private static final VoxelShape SOUTH_R = VoxelShapeUtils.rotate(NORTH_R, Rotation.CLOCKWISE_180);
    private static final VoxelShape EAST_R = VoxelShapeUtils.rotate(NORTH_R, Rotation.CLOCKWISE_90);

    private int lowThreshold = 20;
    private int highThreshold = 80;

    public CoalGenBlock() {
        super(Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.4F, 5).setRequiresTool().notSolid(), regName, ModelFileHelper.createModelFile(new ResourceLocation(ONIUtils.MODID, "models/block/machines/coal_generator")), 0);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
            if (tileEntity instanceof CoalGenTE) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("oniutils.gui.machines.coal_gen");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new CoalGenContainer(i, world, pos, playerInventory, playerEntity);
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
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getTileEntity(pos) instanceof CoalGenTE) {
            CoalGenTE te = (CoalGenTE) world.getTileEntity(pos);
            if (te != null) {
                if (te.hasItem()) {
                    ISHandlerHelper.dropInventory(te, world, state, pos, te.getInvSize());
                }
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        switch (state.get(BlockStateProperties.FACING)) {
            case NORTH:
                return side == Direction.EAST;
            case SOUTH:
                return side == Direction.WEST;
            case EAST:
                return side == Direction.SOUTH;
            case WEST:
                return side == Direction.NORTH;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.COAL_GEN_TE.get().create();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
            case SOUTH:
                return SOUTH_R;
            case EAST:
                return EAST_R;
            case WEST:
                return WEST_R;
            default:
                return NORTH_R;
        }
    }
}
