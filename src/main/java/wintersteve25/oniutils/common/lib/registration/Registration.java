package wintersteve25.oniutils.common.lib.registration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.lib.helper.RegistryHelper;
import wintersteve25.oniutils.common.lib.helper.TextHelper;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ONIUtils.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ONIUtils.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        ONIBlocks.register();

        for (ONIBaseRock b : ONIBlocks.rocksList){
            RegistryHelper.register(TextHelper.langToReg(b.getRegName()), () -> b);
        }

        ONIUtils.LOGGER.info("ONIUtils Registration Completed");
    }
}
