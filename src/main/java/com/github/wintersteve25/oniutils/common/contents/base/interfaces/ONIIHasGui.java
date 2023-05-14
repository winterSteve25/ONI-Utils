package com.github.wintersteve25.oniutils.common.contents.base.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseMachine;

/**
 * Should be implemented on a {@link ONIBaseMachine}
 * Or to be used in {@link com.github.wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder#container(ONIIHasGui)}
 */
public interface ONIIHasGui {
    AbstractContainerMenu container(int i, Level world, BlockPos pos, Inventory playerInventory, Player playerEntity);

    Component machineName();
}
