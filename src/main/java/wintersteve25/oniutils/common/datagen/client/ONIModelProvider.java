package wintersteve25.oniutils.common.datagen.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.api.ONIIRegistryObject;
import wintersteve25.oniutils.common.contents.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.contents.base.ONIIItem;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIItems;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModification;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

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
        for (ONIIRegistryObject<Block> b : ONIBlocks.blockList.keySet()) {
            Block block = b.get();
            if (!(block instanceof ONIIHasGeoItem) && b.doModelGen()) {
                if (block instanceof ONIBaseDirectional) {
                    withExistingParent(MiscHelper.langToReg(b.getRegName()), ((ONIBaseDirectional) block).getModelFile().getLocation());
                } else {
                    withExistingParent(MiscHelper.langToReg(b.getRegName()), modLoc("block/" + MiscHelper.langToReg(b.getRegName())));
                }
            }
        }
        for (ONIIRegistryObject<Item> i : ONIItems.itemRegistryList) {
            if (i.doModelGen()) {
                Item item = i.get();
                if (item instanceof ONIModification) {
                    ONIModification mod = (ONIModification) item;
                    String name = i.getRegName();
                    String processedName = MiscHelper.langToReg(mod.getModType().getName()) + name.charAt(name.length() - 1);
                    builder(MiscHelper.langToReg(name), "modifications/" + processedName);
                } else {
                    String name = MiscHelper.langToReg(i.getRegName());
                    if (item instanceof ONIIItem) {
                        builder(name, ((ONIIItem) item).getItemCategory().getPathName() + name);
                    } else {
                        builder(name, name);
                    }
                }
            }
        }
    }
}
