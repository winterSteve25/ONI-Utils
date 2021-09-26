package wintersteve25.oniutils.common.blocks.modules.power.coal;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.blocks.base.ONIContainerImpl;
import wintersteve25.oniutils.common.init.ONIBlocks;

import java.util.List;

public class CoalGenContainer extends ONIContainerImpl {
    public CoalGenContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(windowId, world, pos, playerInventory, player, ONIBlocks.COAL_GEN_CONTAINER.get());
    }

    @Override
    public boolean shouldAddPlaySlots() {
        return true;
    }

    @Override
    public boolean shouldTrackPower() {
        return true;
    }

    @Override
    public boolean shouldTrackWorking() {
        return true;
    }

    @Override
    public boolean shouldTrackPowerCapacity() {
        return true;
    }

    @Override
    public boolean shouldTrackProgress() {
        return true;
    }

    @Override
    public boolean shouldAddInternalInventory() {
        return true;
    }

    @Override
    public List<Tuple<Integer, Integer>> internalInventorySlotsArrangement() {
        return Lists.newArrayList(new Tuple<>(55, 32));
    }
}
