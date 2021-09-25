package wintersteve25.oniutils.common.blocks.modules.oxygen.algae;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.oniutils.client.renderers.geckolibs.base.ONIIHasGeoItem;
import wintersteve25.oniutils.common.blocks.base.ONIBaseMachineAnimated;

public class AlgaeDiffuserBlock extends ONIBaseMachineAnimated implements ONIIHasGeoItem {
    private static final String regName = "Algae Diffuser";

    public AlgaeDiffuserBlock() {
        super(Properties.create(Material.IRON), regName, null, 0, AlgaeDiffuserTE.class);
    }

    @Override
    public String machineName() {
        return null;
    }

    @Override
    public Container container(int i, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;
    }
}
