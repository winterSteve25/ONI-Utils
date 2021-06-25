package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseRock;
import wintersteve25.oniutils.common.blocks.modules.resources.slime.SlimeBlock;
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
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        for (ONIBaseRock b : ONIBlocks.rocksListNoDataGen) {
            if (!b.getRegName().equals(ONIBlocks.PollutedIce.getRegName())) {
                lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
            }
        }

        for (ONIBaseDirectional b : ONIBlocks.direList) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        for (ONIBaseDirectional b : ONIBlocks.direListNoDataGen) {
            lootTables.putIfAbsent(b, createStandardTable(MiscHelper.langToReg(b.getRegName()), b));
        }

        lootTables.putIfAbsent(ONIBlocks.Slime, createStandardTable(MiscHelper.langToReg(ONIBlocks.Slime.getRegName()), ONIBlocks.Slime));
    }
}