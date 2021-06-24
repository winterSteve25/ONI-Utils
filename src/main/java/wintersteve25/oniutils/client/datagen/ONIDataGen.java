package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import wintersteve25.oniutils.ONIUtils;

@Mod.EventBusSubscriber(modid = ONIUtils.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ONIDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            gen.addProvider(new ONILootTableProvider(gen));
        }

        if (event.includeClient()) {
            gen.addProvider(new ONIStateProvider(gen, existingFileHelper));
            gen.addProvider(new ONIModelProvider(gen, existingFileHelper));

            //en_US
            gen.addProvider(new ONIEngLangProvider(gen));
        }
    }
}
