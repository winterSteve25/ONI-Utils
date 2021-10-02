package wintersteve25.oniutils.common.blocks.modules.furniture;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;

public class LightBulbBlock extends ONIBaseDirectional {
    //public static BooleanProperty ON = BooleanProperty.create("on");

    public LightBulbBlock() {
        super("Light Bulb", Properties.create(Material.GLASS).hardnessAndResistance(2, 2).notSolid(), null, 0);

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
