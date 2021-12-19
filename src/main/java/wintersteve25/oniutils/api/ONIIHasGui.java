package wintersteve25.oniutils.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;

/**
 * Should be implemented on a {@link wintersteve25.oniutils.common.contents.base.ONIBaseMachine}
 * Or to be used in {@link ONIBlockBuilder#container(ONIIHasGui)}
 */
public interface ONIIHasGui {
    Container container(int i, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity);

    ITextComponent machineName();
}
