package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

public class ONIModelProvider extends ItemModelProvider {
    public ONIModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ONIUtils.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("igneous_rock", modLoc("block/igneous_rock"));

        for (ONIBaseRock b : ONIBlocks.rocksList) {
            withExistingParent(TextHelper.langToReg(b.getRegName()), modLoc("block/" + TextHelper.langToReg(b.getRegName())));
        }

        ModelFile generated = getExistingFile(mcLoc("item/generated"));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
