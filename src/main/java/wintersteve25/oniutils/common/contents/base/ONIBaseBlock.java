package wintersteve25.oniutils.common.contents.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.api.ONIIRegistryObject;
import wintersteve25.oniutils.api.ONIIStateFluidLoggable;
import wintersteve25.oniutils.api.functional.IRenderTypeProvider;
import wintersteve25.oniutils.api.functional.IVoxelShapeProvider;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ONIBaseBlock extends Block implements ONIIStateFluidLoggable, ONIIRegistryObject<Block> {

    private final String regName;

    // block builder properties
    private IVoxelShapeProvider hitBox;
    private IRenderTypeProvider renderType;
    private Supplier<Item> blockItem;
    private boolean doModelGen = false;
    private boolean doStateGen = false;
    private boolean doLangGen = true;
    private boolean doLootTableGen = true;

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = regName;

//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = regName;

//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(soundType).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = regName;

//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

    public ONIBaseBlock(String regName, Properties properties) {
        super(properties);
        this.regName = regName;

//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

    @Override
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return this.hasTileEntity(state) ? PushReaction.BLOCK : super.getPushReaction(state);
    }

//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        super.fillStateContainer(builder);
//        builder.add(this.getFluidLoggedProperty());
//    }

//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
//        return this.getDefaultState().with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return hitBox == null ? super.getShape(state, worldIn, pos, context) : hitBox.createShape(state, worldIn, pos, context);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return renderType == null ? super.getRenderType(state) : renderType.createRenderType(state);
    }

    @Override
    public boolean doModelGen() {
        return doModelGen;
    }

    @Override
    public boolean doStateGen() {
        return doStateGen;
    }

    @Override
    public boolean doLangGen() {
        return doLangGen;
    }

    @Override
    public boolean doLootTableGen() {
        return doLootTableGen;
    }

    public ONIBaseBlock setDoModelGen(boolean doModelGen) {
        this.doModelGen = doModelGen;
        return this;
    }

    public ONIBaseBlock setDoStateGen(boolean doStateGen) {
        this.doStateGen = doStateGen;
        return this;
    }

    public ONIBaseBlock setDoLangGen(boolean doLangGen) {
        this.doLangGen = doLangGen;
        return this;
    }

    public ONIBaseBlock setDoLootTableGen(boolean doLootTableGen) {
        this.doLootTableGen = doLootTableGen;
        return this;
    }

    @Override
    public Block get() {
        return this;
    }

    @Override
    public String getRegName() {
        return regName;
    }

    public IVoxelShapeProvider getHitBox() {
        return hitBox;
    }

    public void setHitBox(IVoxelShapeProvider hitBox) {
        this.hitBox = hitBox;
    }

    public IRenderTypeProvider getRenderType() {
        return renderType;
    }

    public void setRenderType(IRenderTypeProvider renderType) {
        this.renderType = renderType;
    }

    public Supplier<Item> getBlockItem() {
        return blockItem;
    }

    public void setBlockItem(Supplier<Item> blockItem) {
        this.blockItem = blockItem;
    }
}
