package com.github.wintersteve25.oniutils.common.datagen.server;

import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import com.github.wintersteve25.oniutils.common.registration.block.ONIBlockRegistryData;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import com.github.wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class ONILootTableProvider extends LootTableBase {
    public ONILootTableProvider(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
    }

    @Override
    protected void addTables() {
        standardTables();
    }

    private void standardTables() {
        for (BlockRegistryObject<? extends Block, ? extends BlockItem> b : ONIBlocks.BLOCKS.getAllBlocks().keySet()) {
            ONIBlockRegistryData data = ONIBlocks.BLOCKS.getAllBlocks().get(b);
            if (data.isDoLootTableGen()) lootTables.putIfAbsent(b.getBlock(), createStandardTable(b.getName(), b.getBlock()));
        }
    }
}