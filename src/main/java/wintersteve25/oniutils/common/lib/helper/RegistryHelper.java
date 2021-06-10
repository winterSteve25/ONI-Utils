package wintersteve25.oniutils.common.lib.helper;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
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

    public static <I extends TileEntityType<?>> RegistryObject<I> registerTE(String name, Supplier<? extends I> te) {
        return Registration.TE.register(name, te);
    }
}
