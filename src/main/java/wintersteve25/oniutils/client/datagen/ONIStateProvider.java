package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIStateProvider extends BlockStateProvider {

    public ONIStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ONIUtils.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ONIBlocks.IgneousRock.get());
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            simpleBlock(b);
        }
    }
}
