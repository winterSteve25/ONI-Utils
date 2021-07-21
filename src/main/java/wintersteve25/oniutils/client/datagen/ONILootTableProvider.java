package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class ONILootTableProvider extends LootTableBase {
    public ONILootTableProvider(DataGenerator p_i50789_1_) {
        super(p_i50789_1_);
    }

    @Override
    protected void addTables() {
        standardTables();
    }

    private void standardTables() {
        for (ONIBaseBlock b : ONIBlocks.blockList.keySet()) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        for (ONIBaseBlock b : ONIBlocks.blockNoDataList.keySet()) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        for (ONIBaseDirectional b : ONIBlocks.directionalList.keySet()) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        for (ONIBaseDirectional b : ONIBlocks.directionalNoDataList.keySet()) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }
    }
}