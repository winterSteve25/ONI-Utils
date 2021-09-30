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
        int defaultSpeed = 10;
        float bonusSpeed = 0;

        for (ItemStack stack : installedMods()) {
            if (stack.getItem() instanceof ONIBaseModification) {
                ONIBaseModification modification = (ONIBaseModification) stack.getItem();
                EnumModifications modType = modification.getModType();
                if (modType == EnumModifications.SPEED) {
                    float percentage = (float) (ONIBaseModification.getBonusDataFromItemStack(stack) / 100.0);
                    bonusSpeed += defaultSpeed * percentage;
                }
            }
        }

        return Math.round(defaultSpeed + bonusSpeed);
    }

    public int getPlasmaOutputPerTick(int totalTicks, int totalOutput) {
        int totalAmountOfTicksToComplete = totalTicks/getProgressSpeed();
        int outputPerTick = totalOutput/totalAmountOfTicksToComplete;
        ONIUtils.LOGGER.info(outputPerTick + ", {}", totalAmountOfTicksToComplete);
        return outputPerTick;
    }
}
