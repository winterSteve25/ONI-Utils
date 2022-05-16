package wintersteve25.oniutils.common.registration.item;

import mekanism.common.registration.WrappedForgeDeferredRegister;
import mekanism.common.registration.impl.EntityTypeRegistryObject;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.oniutils.ONIUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ONIItemDeferredRegister extends WrappedForgeDeferredRegister<Item> {
    private final Map<ItemRegistryObject<? extends Item>, ONIItemRegistryData> allItems = new HashMap<>();

    public ONIItemDeferredRegister(String modid) {
        super(modid, ForgeRegistries.ITEMS);
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Function<Item.Properties, ITEM> sup) {
        return register(name, () -> sup.apply(ONIUtils.defaultProperties()));
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Function<Item.Properties, ITEM> sup, boolean doModelGen, boolean doLangGen) {
        return register(name, () -> sup.apply(ONIUtils.defaultProperties()), doModelGen, doLangGen);
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Function<Item.Properties, ITEM> sup, ONIItemRegistryData registryData) {
        return register(name, () -> sup.apply(ONIUtils.defaultProperties()), registryData);
    }

    public <ENTITY extends Mob> ItemRegistryObject<ForgeSpawnEggItem> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider, int primaryColor, int secondaryColor) {
        return register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", props -> new ForgeSpawnEggItem(entityTypeProvider, primaryColor, secondaryColor, props));
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Supplier<? extends ITEM> sup) {
        ItemRegistryObject<ITEM> registeredItem = register(name, sup, ItemRegistryObject::new);
        allItems.put(registeredItem, new ONIItemRegistryData(true, true));
        return registeredItem;
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Supplier<? extends ITEM> sup, boolean doModelGen, boolean doLangGen) {
        ItemRegistryObject<ITEM> registeredItem = register(name, sup, ItemRegistryObject::new);
        allItems.put(registeredItem, new ONIItemRegistryData(doModelGen, doLangGen));
        return registeredItem;
    }

    public <ITEM extends Item> ItemRegistryObject<ITEM> register(String name, Supplier<? extends ITEM> sup, ONIItemRegistryData data) {
        ItemRegistryObject<ITEM> registeredItem = register(name, sup, ItemRegistryObject::new);
        allItems.put(registeredItem, data);
        return registeredItem;
    }

    public Map<ItemRegistryObject<? extends Item>, ONIItemRegistryData> getAllItems() {
        return allItems;
    }
}
