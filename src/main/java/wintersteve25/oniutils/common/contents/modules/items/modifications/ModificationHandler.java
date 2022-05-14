package wintersteve25.oniutils.common.contents.modules.items.modifications;

import net.minecraft.world.item.ItemStack;
import wintersteve25.oniutils.common.contents.base.enums.EnumModifications;

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
        int defaultSpeed = 10;
        float bonusSpeed = 0;

        for (ItemStack stack : installedMods()) {
            if (stack.getItem() instanceof ONIModificationItem) {
                ONIModificationItem modification = (ONIModificationItem) stack.getItem();
                EnumModifications modType = modification.getModType();
                if (modType == EnumModifications.SPEED) {
                    float percentage = (float) (ONIModificationItem.getBonusDataFromItemStack(stack) / 100.0);
                    bonusSpeed += defaultSpeed * percentage;
                }
            }
        }

        return Math.round(defaultSpeed + bonusSpeed);
    }

    public int getPlasmaOutputPerTick(int totalTicks, int totalOutput) {
        int totalAmountOfTicksToComplete = totalTicks/getProgressSpeed();
        return totalOutput/totalAmountOfTicksToComplete;
    }

    public int getRequiredSkillLevel(int baseLevel) {
        float bonus = 0;

        for (ItemStack stack : installedMods()) {
            if (stack.getItem() instanceof ONIModificationItem) {
                ONIModificationItem modification = (ONIModificationItem) stack.getItem();
                EnumModifications modType = modification.getModType();
                if (modType == EnumModifications.COMPLEXITY) {
                    float percentage = (float) (ONIModificationItem.getBonusDataFromItemStack(stack) / 100.0);
                    bonus += baseLevel * percentage;
                }
            }
        }
        return Math.round(baseLevel + bonus);
    }
}
