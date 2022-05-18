package wintersteve25.oniutils.common.datagen.client;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import wintersteve25.oniutils.common.contents.modules.blocks.power.cables.PowerCableBlock;
import wintersteve25.oniutils.common.registration.block.ONIBlockRegistryData;
import wintersteve25.oniutils.common.registration.block.ONIDirectionalBlockRegistryData;
import wintersteve25.oniutils.common.registration.item.ONIItemRegistryData;
import wintersteve25.oniutils.common.registries.ONIBlocks;
import wintersteve25.oniutils.common.registries.ONIItems;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;

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
        for (BlockRegistryObject<? extends Block, ? extends BlockItem> b : ONIBlocks.BLOCKS.getAllBlocks().keySet()) {
            ONIBlockRegistryData data = ONIBlocks.BLOCKS.getAllBlocks().get(b);
            if (data.isDoModelGen()) {
                if (data instanceof ONIDirectionalBlockRegistryData directionalData) {
                    withExistingParent(b.getName(), directionalData.getModelFile().getLocation());
                } else if (b.getBlock() instanceof PowerCableBlock) {
                    withExistingParent(b.getName(), modLoc("block/wires/" + b.getName() + "/main"));
                } else {
                    withExistingParent(b.getName(), modLoc("block/" + b.getName()));
                }
            }
        }

        for (ItemRegistryObject<? extends Item> i : ONIItems.ITEMS.getAllItems().keySet()) {
            ONIItemRegistryData data = ONIItems.ITEMS.getAllItems().get(i);
            if (data.isDoModelGen()) {
                Item item = i.get();
                if (item instanceof ONIModificationItem mod) {
                    String name = i.getName();
                    String processedName = mod.getModType().getName() + name.charAt(name.length() - 1);
                    builder(name, "modifications/" + processedName);
                } else {
                    String name = i.getName();
                    if (item instanceof ONIIItem) {
                        builder(name, ((ONIIItem) item).getONIItemCategory().getPathName() + name);
                    } else {
                        builder(name, name);
                    }
                }
            }
        }
    }
}