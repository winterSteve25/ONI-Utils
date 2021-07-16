package wintersteve25.oniutils.common.utils.helper;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.utils.registration.Registration;

import java.util.function.Supplier;

public class RegistryHelper {
    public static <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> block) {
        RegistryObject<I> registryObject = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().group(ONIUtils.creativeTab)));
        return registryObject;
    }

    public static <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> block, BlockItem blockItem) {
        RegistryObject<I> registryObject = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> blockItem);
        return registryObject;
    }

    public static <I extends TileEntityType<?>> RegistryObject<I> registerTE(String name, Supplier<? extends I> te) {
        return Registration.TE.register(name, te);
    }

    public static <I extends ContainerType<?>> RegistryObject<I> registerContainer(String name, Supplier<? extends I> container) {
        return Registration.CONTAINER.register(name, container);
    }

    public static <I extends Effect> RegistryObject<I> registerEffects(String name, Supplier<? extends I> effect) {
        return Registration.EFFECTS.register(name, effect);
    }

    public static <I extends SoundEvent> RegistryObject<I> registerSounds(String name, Supplier<? extends I> sound) {
        return Registration.SOUND.register(name, sound);
    }

    public static SoundEvent createSound(String name) {
        return new SoundEvent(new ResourceLocation(ONIUtils.MODID, name));
    }
}
