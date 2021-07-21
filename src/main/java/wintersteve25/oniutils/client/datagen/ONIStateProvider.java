package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIStateProvider extends BlockStateProvider {

    public ONIStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ONIUtils.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        autoGenStatesAndModels();

        simpleBlock(ONIBlocks.Slime);
//        simpleBlock(ONIBlocks.BULB_BLOCK, new ModelFile(new ResourceLocation(ONIUtils.MODID, "block/furniture/light_bulb")) {
//            @Override
//            protected boolean exists() {
//                return true;
//            }
//        });
    }

    private void autoGenStatesAndModels() {
        for (ONIBaseBlock b : ONIBlocks.blockList.keySet()) {
            if (b != null) {
                simpleBlock(b);
            }
        }

        for (ONIBaseDirectional b : ONIBlocks.directionalList.keySet()) {
            if (b != null) {
                if (b.getAngelOffset() == 0) {
                    directionalBlock(b, b.getModelFile());
                } else {
                    directionalBlock(b, b.getModelFile(), b.getAngelOffset());
                }
            }
        }
    }
}
