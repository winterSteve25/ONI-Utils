package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class ONIBaseDirectional extends DirectionalBlock {

    private final String regName;
    @Nullable
    private final ModelFile modelFile;
    private final int angelOffset;

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, @Nullable ModelFile modelFile, int angelOffset) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance));
        this.regName = regName;
        this.modelFile = modelFile;
        this.angelOffset = angelOffset;

        BlockState state = this.getStateContainer().getBaseState();
        state.with(FACING, Direction.NORTH);

        this.setDefaultState(state);
    }

    public ONIBaseDirectional(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material, @Nullable ModelFile modelFile, int angelOffset) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance));
        this.regName = regName;
        this.modelFile = modelFile;
        this.angelOffset = angelOffset;

        BlockState state = this.getStateContainer().getBaseState();
        state.with(FACING, Direction.NORTH);

        this.setDefaultState(state);
    }

    public ONIBaseDirectional(String regName, Properties properties, @Nullable ModelFile modelFile, int angelOffset) {
        super(properties);
        this.regName = regName;
        this.modelFile = modelFile;
        this.angelOffset = angelOffset;

        BlockState state = this.getStateContainer().getBaseState();
        state.with(FACING, Direction.NORTH);
//        state.with(getFluidLoggedProperty(), 0);

        this.setDefaultState(state);
    }

    public String getRegName() {
        return this.regName;
    }

    @Nullable
    public ModelFile getModelFile() {
        return this.modelFile;
    }

    public int getAngelOffset() {
        return angelOffset;
    }

    public void initDirectional(ONIBaseDirectional b, Item i) {
        ONIBlocks.directionalList.put(b, i);
    }

    public void initDirectionalNoData(ONIBaseDirectional b, Item i) {
        ONIBlocks.directionalNoDataList.put(b, i);
    }

    @Override
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return this.hasTileEntity(state) ? PushReaction.BLOCK : super.getPushReaction(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.fillStateContainer(stateBuilder);
        stateBuilder.add(FACING);
//        stateBuilder.add(this.getFluidLoggedProperty());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.with(FACING, mirror.mirror(blockState.get(FACING)));
    }

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
}
