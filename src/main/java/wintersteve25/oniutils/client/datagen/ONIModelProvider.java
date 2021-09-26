package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIItems;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIItem;
import wintersteve25.oniutils.common.items.modules.modifications.ONIBaseModification;
import wintersteve25.oniutils.common.utils.MiscHelper;

public class ONIModelProvider extends ItemModelProvider {
    public ONIModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ONIUtils.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        autoGenModels();
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder builder(String path, String name) {
        return getBuilder(path).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }

    private void autoGenModels() {
        for (ONIBaseBlock b : ONIBlocks.blockList.keySet()) {
            if (!(b instanceof ONIIHasGeoItem)) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
            }
        }
        for (ONIBaseDirectional b : ONIBlocks.directionalList.keySet()) {
            if (!(b instanceof ONIIHasGeoItem)) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
            }
        }
        for (ONIBaseBlock b : ONIBlocks.blockNoDataList.keySet()) {
            if (!(b instanceof ONIIHasGeoItem)) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
            }
        }
        for (ONIBaseDirectional b : ONIBlocks.directionalNoDataList.keySet()) {
            if (MiscHelper.langToReg(b.getRegName()).equals("sedimentary_rock")) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/rocks/" + MiscHelper.langToReg(b.getRegName())));
            } else if (!(b instanceof ONIIHasGeoItem)) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
            }
        }
        for (ONIBaseSixWaysBlock b : ONIBlocks.sixWaysList.keySet()) {
            if (!(b instanceof ONIIHasGeoItem)) {
                withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
            }
        }
        for (ONIIItem i : ONIItems.itemRegistryList) {
            if (i.get() instanceof ONIBaseModification) {
                ONIBaseModification mod = (ONIBaseModification) i.get();
                String name = i.getRegName();
                String processedName = MiscHelper.langToReg(mod.getModType().getName()) + name.charAt(name.length()-1);
                builder(MiscHelper.langToReg(name), "modifications/" + processedName);
            } else {
                String name = i.getRegName();
                builder(name, name);
            }
        }
    }
}
