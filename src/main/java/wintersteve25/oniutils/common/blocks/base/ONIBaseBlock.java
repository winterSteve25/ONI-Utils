package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import wintersteve25.oniutils.common.init.ONIBlocks;

import javax.annotation.Nonnull;

public class ONIBaseBlock extends Block /*implements ONIIStateFluidLoggable*/ {

    private final String regName;

    public ONIBaseBlock(int harvestLevel, float hardness, float resistance) {
        super(Properties.create(Material.ROCK).harvestLevel(harvestLevel).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).hardnessAndResistance(hardness, resistance).setRequiresTool());
        this.regName = null;

//        setDefaultState(this.getStateContainer().getBaseState().with(this.getFluidLoggedProperty(), 0));
    }

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

    public String getRegName() {
        return this.regName;
    }

    public void initBlock(ONIBaseBlock b, Item i) {
        ONIBlocks.blockList.put(b, i);
    }

    public void initBlockNoData(ONIBaseBlock b, Item i) {
        ONIBlocks.blockNoDataList.put(b, i);
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
//
//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
//        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
//
//        return this.getDefaultState().with(this.getFluidLoggedProperty(), this.getSupportedFluidPropertyIndex(fluidState.getFluid()));
//    }
}
