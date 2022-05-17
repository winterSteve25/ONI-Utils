package wintersteve25.oniutils.common.registration.block;

import mekanism.common.registration.DoubleForgeDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.contents.base.items.ONIBaseItemBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ONIBlockDeferredRegister extends DoubleForgeDeferredRegister<Block, Item> {

    private final Map<BlockRegistryObject<? extends Block, ? extends BlockItem>, ONIBlockRegistryData> allBlocks = new HashMap<>();

    public ONIBlockDeferredRegister(String modid) {
        super(modid, ForgeRegistries.BLOCKS, ForgeRegistries.ITEMS);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier) {
        return registerDefaultProperties(name, blockSupplier, ONIBaseItemBlock::new);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier, boolean doStateGen, boolean doModelGen, boolean doLangGen, boolean doLootableGen) {
        return registerDefaultProperties(name, blockSupplier, ONIBaseItemBlock::new, doStateGen, doModelGen, doLangGen, doLootableGen);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier, ONIBlockRegistryData data) {
        return registerDefaultProperties(name, blockSupplier, ONIBaseItemBlock::new, data);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> registerDefaultProperties(String name, Supplier<B> blockSupplier, BiFunction<B, Item.Properties, BlockItem> itemCreator) {
        return register(name, blockSupplier, block -> itemCreator.apply(block, ONIUtils.defaultProperties()));
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> registerDefaultProperties(String name, Supplier<B> blockSupplier, BiFunction<B, Item.Properties, BlockItem> itemCreator, boolean doStateGen, boolean doModelGen, boolean doLangGen, boolean doLootableGen) {
        return register(name, blockSupplier, block -> itemCreator.apply(block, ONIUtils.defaultProperties()), doStateGen, doModelGen, doLangGen, doLootableGen);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> registerDefaultProperties(String name, Supplier<B> blockSupplier, BiFunction<B, Item.Properties, BlockItem> itemCreator, ONIBlockRegistryData data) {
        return register(name, blockSupplier, block -> itemCreator.apply(block, ONIUtils.defaultProperties()), data);
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier, Function<B, BlockItem> itemCreator) {
        BlockRegistryObject<B, BlockItem> registeredBlock = register(name, blockSupplier, itemCreator, BlockRegistryObject::new);
        allBlocks.put(registeredBlock, new ONIBlockRegistryData());
        return registeredBlock;
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier, Function<B, BlockItem> itemCreator, boolean doStateGen, boolean doModelGen, boolean doLangGen, boolean doLootableGen) {
        BlockRegistryObject<B, BlockItem> registeredBlock = register(name, blockSupplier, itemCreator, BlockRegistryObject::new);
        allBlocks.put(registeredBlock, new ONIBlockRegistryData(doStateGen, doModelGen, doLangGen, doLootableGen));
        return registeredBlock;
    }

    public <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<B> blockSupplier, Function<B, BlockItem> itemCreator, ONIBlockRegistryData registryData) {
        BlockRegistryObject<B, BlockItem> registeredBlock = register(name, blockSupplier, itemCreator, BlockRegistryObject::new);
        allBlocks.put(registeredBlock, registryData);
        return registeredBlock;
    }

    public Map<BlockRegistryObject<? extends Block, ? extends BlockItem>, ONIBlockRegistryData> getAllBlocks() {
        return allBlocks;
    }
}
