package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.helper.MiscHelper;

public class ONIEngLangProvider extends LanguageProvider {
    public ONIEngLangProvider(DataGenerator gen) {
        super(gen, ONIUtils.MODID, "en_US");
    }

    @Override
    protected void addTranslations() {
        //simple blocks
        add(ONIBlocks.IgneousRock.get(), "Igneous Rock");
        add("block.oniutils.oxylite", "Oxylite");

        for (ONIBaseRock b : ONIBlocks.rocksList) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseRock b : ONIBlocks.rocksListNoDataGen) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }

        for (ONIBaseDirectional b : ONIBlocks.direList) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseDirectional b : ONIBlocks.direListNoDataGen) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }

        //item groups
        add("itemGroup.oniutils", "Veiled Ascent");

        //messages
        add("oniutils.message.germs.infectEntity", "Infected interacted entity with %s");
        add("oniutils.message.germs.infectItem", "Infected item(s) with %s");
        add("oniutils.message.germs.infectPlayer", "Infected with %s");

        add("death.attack.oniutils.oxygen", "%1$s forgot to breathe");
        add("death.attack.oniutils.germ", "%1$s didn't take the vaccine");
    }
}
