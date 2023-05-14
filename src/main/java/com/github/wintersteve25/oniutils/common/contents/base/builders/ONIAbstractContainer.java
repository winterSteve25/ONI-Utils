package com.github.wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.util.Tuple;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import com.github.wintersteve25.oniutils.common.contents.base.interfaces.ONIIModifiable;
import com.github.wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import com.github.wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationContext;
import com.github.wintersteve25.oniutils.common.utils.SlotArrangement;

import java.util.List;

public class ONIAbstractContainer extends ONIBaseContainer {
    protected ONIAbstractContainer(
            int windowId,
            Level world,
            BlockPos pos,
            Inventory playerInventory,
            Player player,
            MenuType container,
            boolean shouldAddPlayerSlots,
            boolean shouldTrackPower,
            boolean shouldTrackPowerCapacity,
            boolean shouldTrackWorking,
            boolean shouldTrackProgress,
            boolean shouldTrackTotalProgress,
            boolean shouldAddInternalInventory,
            List<SlotArrangement> internalSlotArrangement,
            Tuple<Integer, Integer> playerSlotStart
    ) {
        super(windowId, world, pos, playerInventory, player, container);

        if (shouldAddInternalInventory) {
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    if (shouldAddModificationSlots()) {
                        if (h instanceof ModificationContext.ONICombinedInvWrapper combinedInvWrapper) {
                            int index = 0;

                            for(SlotArrangement arrangement : internalSlotArrangement) {
                                addMachineSlot(combinedInvWrapper.getHandlerFromIndex(arrangement.getIndexOfItemHandler()), index, arrangement);
                                index++;
                            }

                            addModificationSlots(combinedInvWrapper.getHandlerFromIndex(1));
                        }
                    } else {
                        int index = 0;

                        for(SlotArrangement arrangement : internalSlotArrangement) {
                            addMachineSlot(h, index, arrangement);
                            index++;
                        }
                    }
                });
            }
        }

        if (shouldAddPlayerSlots) {
            addPlayerSlots(playerSlotStart.getA(), playerSlotStart.getB());
        }

        if (shouldTrackPower) {
            trackPower();
        }

        if (shouldTrackWorking) {
            trackWorking();
        }

        if (shouldTrackPowerCapacity) {
            trackPowerCapacity();
        }

        if (shouldTrackProgress) {
            trackProgress();
        }

        if (shouldTrackTotalProgress) {
            trackTotalProgress();
        }

        trackRedstoneInverted();
    }

    public boolean shouldAddModificationSlots() {
        return tileEntity instanceof ONIIModifiable;
    }
}
