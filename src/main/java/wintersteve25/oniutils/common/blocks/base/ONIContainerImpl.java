package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIHasRedstoneOutput;
import wintersteve25.oniutils.common.blocks.base.interfaces.ONIIModifiable;
import wintersteve25.oniutils.common.items.modules.modifications.ModificationContext;

import java.util.List;

public abstract class ONIContainerImpl extends ONIBaseContainer {
    protected ONIContainerImpl(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, ContainerType container) {
        super(windowId, world, pos, playerInventory, player, container);

        if (shouldAddInternalInventory()) {
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (shouldAddModificationSlots()) {
                        if (h instanceof ModificationContext.ONICombinedInvWrapper) {
                            ModificationContext.ONICombinedInvWrapper combinedInvWrapper = (ModificationContext.ONICombinedInvWrapper) h;

                            int index = 0;

                            for(Tuple<Integer, Integer> tuple : internalInventorySlotsArrangement()) {
                                addMachineSlot(combinedInvWrapper.getHandlerFromIndex(0), index, tuple);
                                index++;
                            }

                            addModificationSlots(combinedInvWrapper.getHandlerFromIndex(1));
                        }
                    } else {
                        int index = 0;

                        for(Tuple<Integer, Integer> tuple : internalInventorySlotsArrangement()) {
                            addMachineSlot(h, index, tuple);
                            index++;
                        }
                    }
                });
            }
        }

        if (shouldAddPlaySlots()) {
            addPlayerSlots(playerSlotArrangement().getA(), playerSlotArrangement().getB());
        }

        if (shouldTrackPower()) {
            trackPower();
        }

        if (shouldTrackWorking()) {
            trackWorking();
        }

        if (shouldTrackPowerCapacity()) {
            trackPowerCapacity();
        }

        if (shouldTrackProgress()) {
            trackProgress();
        }

        if (shouldTrackTotalProgress()) {
            trackTotalProgress();
        }

        trackRedstoneInverted();
    }

    public abstract boolean shouldAddPlaySlots();

    public abstract boolean shouldTrackPower();

    public abstract boolean shouldTrackWorking();

    public abstract boolean shouldTrackPowerCapacity();

    public abstract boolean shouldTrackProgress();

    public abstract boolean shouldTrackTotalProgress();

    public abstract boolean shouldAddInternalInventory();

    public abstract List<Tuple<Integer, Integer>> internalInventorySlotsArrangement();

    public boolean shouldAddModificationSlots() {
        return tileEntity instanceof ONIIModifiable;
    }

    public Tuple<Integer, Integer> playerSlotArrangement() {
        return new Tuple<>(8, 88);
    }
}
