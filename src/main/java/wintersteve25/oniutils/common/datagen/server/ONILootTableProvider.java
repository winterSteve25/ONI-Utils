package wintersteve25.oniutils.common.datagen.server;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import wintersteve25.oniutils.common.contents.base.ONIIRegistryObject;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class ONILootTableProvider extends LootTableBase {
    public ONILootTableProvider(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
    }

    @Override
    protected void addTables() {
        standardTables();
    }

    private void standardTables() {
        for (ONIIRegistryObject<Block> b : ONIBlocks.blockList.keySet()) {
            if (b.doLootTableGen()) lootTables.putIfAbsent(b.get(), createStandardTable(MiscHelper.langToReg(b.getRegName()), b.get()));
        }
    }
}