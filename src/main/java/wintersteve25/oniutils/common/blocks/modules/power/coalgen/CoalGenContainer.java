package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;
import wintersteve25.oniutils.common.init.ONIBlocks;

import java.util.ArrayList;
import java.util.List;

public class CoalGenContainer extends ONIBaseContainer {

    private final List<Item> validItems = new ArrayList<>();

    public CoalGenContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(windowId, world, pos, playerInventory, player, ONIBlocks.COAL_GEN_CONTAINER.get());

        if (tileEntity != null) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 55, 32));
            });
        }

        validItems.add(Items.COAL);

        addPlayerSlots(8, 88);
        trackPower();
        trackProgress();
        trackWorking();
    }

    @Override
    public List<Item> validItems() {
        return validItems;
    }
}
