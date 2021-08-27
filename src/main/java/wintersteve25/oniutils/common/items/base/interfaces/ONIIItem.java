package wintersteve25.oniutils.common.items.base.interfaces;

import net.minecraft.item.Item;
import wintersteve25.oniutils.common.init.ONIItems;

public interface ONIIItem {
    String getRegName();

    boolean doRegularDataGen();

    Item get();

    default void init() {
        ONIItems.itemRegistryList.add(this);
    }
}
