package wintersteve25.oniutils.common.utils.helper;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.blocks.base.ONIBaseInvTE;

public class ISHandlerHelper {
    public static void dropInventory(ONIBaseInvTE invTE, World world, BlockState state, BlockPos pos, int inventorySize) {
        if(invTE != null) {
            for(int i = 0; i < inventorySize; i++) {
                ItemStack itemstack = invTE.getItemHandler().getStackInSlot(i);

                if(!itemstack.isEmpty()) {
                    net.minecraft.inventory.InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                }
            }

            world.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }
}
