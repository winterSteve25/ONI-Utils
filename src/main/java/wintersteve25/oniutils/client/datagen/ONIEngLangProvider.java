package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

public class ONIEngLangProvider extends LanguageProvider {
    public ONIEngLangProvider(DataGenerator gen) {
        super(gen, ONIUtils.MODID, "en_US");
    }

    @Override
    protected void addTranslations() {
        add(ONIBlocks.IgneousRock.get(), "Igneous Rock");

        for (ONIBaseRock b : ONIBlocks.rocksList) {
            add("block.oniutils." + TextHelper.langToReg(b.getRegName()), b.getRegName());
        }

        for (ONIBaseRock b : ONIBlocks.rocksListNoDataGen) {
            add("block.oniutils." + TextHelper.langToReg(b.getRegName()), b.getRegName());
        }

        add("itemGroup.oniutils", "Oxygen Not Included Utilities");
    }
}
