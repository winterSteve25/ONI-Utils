package wintersteve25.oniutils.common.lib.helper;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.lib.registration.Registration;

import java.util.function.Supplier;

public class RegistryHelper {
    public static <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> block) {
        RegistryObject<I> registryObject = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(ONIUtils.creativeTab)));
        return registryObject;
    }
}
