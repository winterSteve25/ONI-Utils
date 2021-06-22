package wintersteve25.oniutils.common.blocks.modules.furniture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseMachine;

public class LightBulbBlock extends ONIBaseMachine {
    //public static BooleanProperty ON = BooleanProperty.create("on");

    public LightBulbBlock() {
        super(Properties.of(Material.BUILDABLE_GLASS).strength(2, 2).noOcclusion(), "Light Bulb");

        //registerDefaultState(getStateDefinition().any().setValue(ON, false));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
//        boolean isOn = state.getValue(ON);
//
//        if (isOn) {
            return 15;
//        }
//
//        return super.getLightValue(state, world, pos);
    }
}
