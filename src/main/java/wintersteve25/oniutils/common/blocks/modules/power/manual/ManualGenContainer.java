package wintersteve25.oniutils.common.blocks.modules.power.manual;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.blocks.base.ONIBaseContainer;
import wintersteve25.oniutils.common.init.ONIBlocks;

import java.util.ArrayList;
import java.util.List;

public class ManualGenContainer extends ONIBaseContainer {

    public ManualGenContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(windowId, world, pos, playerInventory, player, ONIBlocks.MANUAL_GEN_CONTAINER.get());

        addPlayerSlots(8, 88);
    }

    @Override
    public String tabTitle() {
        return "oniutils.gui.titles.manual_gen";
    }

    @Override
    public List<Item> validItems() {
        List<Item> validItems = new ArrayList<>();
        validItems.add(Items.AIR);
        return validItems;
    }
}
