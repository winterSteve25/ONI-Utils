package wintersteve25.oniutils.common.items.modules.modifications;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EmptyHandler;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;
import wintersteve25.oniutils.common.items.base.enums.EnumModifications;
import wintersteve25.oniutils.common.utils.ONIInventoryHandler;
import wintersteve25.oniutils.common.utils.ONIModInventoryHandler;

public class ModificationContext {

    private final ONIBaseInvTE parent;

    private final ItemStackHandler upgradeHandler;
    private final int maxModAmount;
    private final EnumModifications[] validMods;

    private final ONICombinedInvWrapper combinedInventory;
    private final LazyOptional<IItemHandler> combinedLazyOptional;

    public ModificationContext(ONIBaseInvTE parent, int maxModAmount, EnumModifications... validMods) {
        this.parent = parent;
        this.maxModAmount = maxModAmount;
        this.validMods = validMods;
        this.upgradeHandler = new ONIModInventoryHandler(this);

        this.combinedInventory = new ONICombinedInvWrapper(parent.getItemHandler(), upgradeHandler);
        this.combinedLazyOptional = LazyOptional.of(() -> combinedInventory);
    }

    public void deserializeNBT(CompoundNBT nbt) {
        upgradeHandler.deserializeNBT(nbt);
    }

    public CompoundNBT serializeNBT() {
        return upgradeHandler.serializeNBT();
    }

    public ONIBaseInvTE getParent() {
        return parent;
    }

    public int getMaxModAmount() {
        return maxModAmount;
    }

    public CombinedInvWrapper getCombinedInventory() {
        return combinedInventory;
    }

    public LazyOptional<IItemHandler> getCombinedLazyOptional() {
        return combinedLazyOptional;
    }

    public EnumModifications[] getValidMods() {
        return validMods;
    }

    public ItemStackHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    public static class ONICombinedInvWrapper extends CombinedInvWrapper {
        public ONICombinedInvWrapper(IItemHandlerModifiable... itemHandler) {
            super(itemHandler);
        }

        @Override
        public IItemHandlerModifiable getHandlerFromIndex(int index) {
            if (index < 0 || index >= itemHandler.length)
            {
                return (IItemHandlerModifiable) EmptyHandler.INSTANCE;
            }
            return itemHandler[index];
        }
    }
}
