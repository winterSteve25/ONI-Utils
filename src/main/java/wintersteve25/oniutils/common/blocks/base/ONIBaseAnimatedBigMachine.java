package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraftforge.client.model.generators.ModelFile;

public abstract class ONIBaseAnimatedBigMachine extends ONIBaseAnimatedMachine {
    public static BooleanProperty IsNonCentral = BooleanProperty.create("non_central");

    public ONIBaseAnimatedBigMachine(Properties properties, String regName, ModelFile modelFile) {
        super(properties, regName, modelFile);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.fillStateContainer(stateBuilder);
        stateBuilder.add(IsNonCentral);
    }


}
