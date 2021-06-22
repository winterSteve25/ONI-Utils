package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseContainer;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class CoalGenContainer extends ONIBaseContainer {

    public CoalGenContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(windowId, world, pos, playerInventory, player, ONIBlocks.COAL_GEN_CONTAINER.get());

        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 54, 31));
            });
        }
        addPlayerSlots(10, 70);
        trackPower();
    }

}
