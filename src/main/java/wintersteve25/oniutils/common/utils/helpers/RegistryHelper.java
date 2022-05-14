package wintersteve25.oniutils.common.utils.helpers;

import mekanism.common.registration.impl.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.registration.Registration;

import java.util.function.Supplier;

public class RegistryHelper {
    public static <B extends Block> BlockRegistryObject<B, BlockItem> register(String name, Supplier<? extends B> block) {
        return Registration.BLOCKS.register(name, block, b -> new BlockItem(b, new Item.Properties().tab(ONIUtils.creativeTab)));
    }

    public static <I extends Item> ItemRegistryObject<I> registerItem(String name, I item) {
        return Registration.ITEMS.register(name, () -> item);
    }

    public static <I extends BlockEntityType<?>> RegistryObject<I> registerTE(String name, Supplier<I> te) {
        return Registration.TE.register(name, te);
    }

    public static <I extends MenuType<?>>RegistryObject<I> registerContainer(String name, Supplier<I> container) {
        return Registration.CONTAINER.register(name, container);
    }

    public static <I extends MobEffect> RegistryObject<I> registerEffects(String name, Supplier<? extends I> effect) {
        return Registration.EFFECTS.register(name, effect);
    }

    public static SoundEventRegistryObject<SoundEvent> registerSounds(String name) {
        return Registration.SOUND.register(name);
    }
}
