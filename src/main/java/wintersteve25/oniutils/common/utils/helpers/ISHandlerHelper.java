package wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import wintersteve25.oniutils.common.contents.base.ONIBaseInvTE;

public class ISHandlerHelper {
    public static void dropInventory(ONIBaseInvTE invTE, World world, BlockState state, BlockPos pos, int inventorySize) {
        if(invTE != null) {
            for(int i = 0; i < inventorySize; i++) {
                ItemStack itemstack = invTE.getItemHandler().getStackInSlot(i);

                if(!itemstack.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                }
            }

            world.notifyNeighborsOfStateChange(pos, state.getBlock());
        }
    }

    public static void dropInventory(IItemHandler inv, World world, BlockState state, BlockPos pos, int inventorySize) {
        if(inv != null) {
            for(int i = 0; i < inventorySize; i++) {
                ItemStack itemstack = inv.getStackInSlot(i);

                if(!itemstack.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                }
            }

            world.notifyNeighborsOfStateChange(pos, state.getBlock());
        }
    }
}
