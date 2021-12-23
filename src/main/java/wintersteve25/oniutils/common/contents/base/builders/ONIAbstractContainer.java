package wintersteve25.oniutils.common.contents.base.builders;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import wintersteve25.oniutils.api.ONIIModifiable;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ModificationContext;
import wintersteve25.oniutils.common.utils.SlotArrangement;

import java.util.List;

public class ONIAbstractContainer extends ONIBaseContainer {

    protected ONIAbstractContainer(
            int windowId,
            World world,
            BlockPos pos,
            PlayerInventory playerInventory,
            PlayerEntity player,
            ContainerType container,
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
                        if (h instanceof ModificationContext.ONICombinedInvWrapper) {
                            ModificationContext.ONICombinedInvWrapper combinedInvWrapper = (ModificationContext.ONICombinedInvWrapper) h;

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
