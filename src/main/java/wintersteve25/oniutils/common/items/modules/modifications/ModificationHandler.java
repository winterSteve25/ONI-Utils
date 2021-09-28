package wintersteve25.oniutils.common.items.modules.modifications;

import net.minecraft.item.ItemStack;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.items.base.enums.EnumModifications;

import java.util.List;

public class ModificationHandler {
    private final ModificationContext context;

    public ModificationHandler(ModificationContext context) {
        this.context = context;
    }

    private List<ItemStack> installedMods() {
        return context.getInstalledMods();
    }

    public int getProgressSpeed() {
        int defaultSpeed = 5;

        for (ItemStack stack : installedMods()) {
            if (stack.getItem() instanceof ONIBaseModification) {
                ONIBaseModification modification = (ONIBaseModification) stack.getItem();
                EnumModifications modType = modification.getModType();
                if (modType == EnumModifications.SPEED) {
                    float percentage = (float) (ONIBaseModification.getBonusDataFromItemStack(stack)/100.0);
                    float bonusSpeed = defaultSpeed*percentage;
                    return (int) (defaultSpeed+bonusSpeed);
                }
            }
        }

        return defaultSpeed;
    }
}
