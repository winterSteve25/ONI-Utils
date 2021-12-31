package wintersteve25.oniutils.common.contents.base;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.Map;

public interface ONIIRegistryObject<TYPE extends IForgeRegistryEntry<?>> {

    boolean doModelGen();

    boolean doStateGen();

    boolean doLangGen();

    boolean doLootTableGen();

    TYPE get();

    String getRegName();

    default void init(List<ONIIRegistryObject<TYPE>> list) {
        list.add(this);
    }

    default <ITEM extends Item> void init(Map<ONIIRegistryObject<TYPE>, ITEM> map, ITEM item) {
        map.putIfAbsent(this, item);
    }
}