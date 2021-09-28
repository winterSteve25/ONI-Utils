package wintersteve25.oniutils.common.init;

import wintersteve25.oniutils.common.items.base.enums.EnumModifications;
import wintersteve25.oniutils.common.items.base.interfaces.ONIIItem;
import wintersteve25.oniutils.common.items.modules.modifications.ONIBaseModification;

import java.util.ArrayList;
import java.util.List;

public class ONIItems {

    public static List<ONIIItem> itemRegistryList = new ArrayList<>();

    public static void register() {
        registerModifications();
    }

    private static void registerModifications() {
        for (EnumModifications modifications : EnumModifications.values()) {
            for (int i = 0; i < modifications.getTiers(); i++) {
                int tier = i+1;
                ONIBaseModification.create("Modification " + modifications.getName() + " Tier " + tier, modifications.getColor(), modifications.getBonusEachTier() * tier, modifications, modifications.getTooltips());
            }
        }
    }
}
