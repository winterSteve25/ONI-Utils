package wintersteve25.oniutils.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

/**
 * Should be implemented on a {@link wintersteve25.oniutils.common.contents.base.ONIBaseMachine}
 * Or to be used in {@link wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder#container(ONIIHasGui)}
 */
public interface ONIIHasGui {
    AbstractContainerMenu container(int i, Level world, BlockPos pos, Inventory playerInventory, Player playerEntity);

    Component machineName();
}
