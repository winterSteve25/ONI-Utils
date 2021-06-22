package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIStateProvider extends BlockStateProvider {

    public ONIStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ONIUtils.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            simpleBlock(b);
        }

        for (ONIBaseDirectional b : ONIBlocks.direList) {
            simpleBlock(b);
        }

        simpleBlock(ONIBlocks.Slime);
        simpleBlock(ONIBlocks.BULB_BLOCK, new ModelFile(new ResourceLocation(ONIUtils.MODID, "block/furniture/light_bulb")) {
            @Override
            protected boolean exists() {
                return true;
            }
        });
    }
}
