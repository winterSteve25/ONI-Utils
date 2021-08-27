package wintersteve25.oniutils.common.init;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIItem;
import wintersteve25.oniutils.common.items.base.modifications.ONIBaseModification;

import java.util.ArrayList;
import java.util.List;

public class ONIItems {
    public static final ONIBaseModification modTest = new ONIBaseModification(new Item.Properties().group(ONIUtils.creativeTab), "Modification", true, TextFormatting.WHITE, 125, new TranslationTextComponent[] {});

    public static List<ONIIItem> itemRegistryList = new ArrayList<>();

    public static void register() {
        modTest.init();
    }
}
