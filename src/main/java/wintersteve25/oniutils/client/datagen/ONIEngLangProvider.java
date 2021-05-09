package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIEngLangProvider extends LanguageProvider {
    public ONIEngLangProvider(DataGenerator gen) {
        super(gen, ONIUtils.MODID, "en_US");
    }

    @Override
    protected void addTranslations() {
        add(ONIBlocks.IgneousRock.get(), "Igneous Rock");
        add("itemGroup.oniutils", "Oxygen Not Included Utilities");
    }
}
