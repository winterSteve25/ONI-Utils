package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class ONIModelProvider extends ItemModelProvider {
    public ONIModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ONIUtils.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
        }
        for (ONIBaseDirectional b : ONIBlocks.direList) {
            withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
        }

        withExistingParent(MiscHelper.langToReg(ONIBlocks.Slime.getRegName()), modLoc("block/" + MiscHelper.langToReg(ONIBlocks.Slime.getRegName())));

        ModelFile generated = getExistingFile(mcLoc("item/generated"));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
