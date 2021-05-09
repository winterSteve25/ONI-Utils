package wintersteve25.oniutils.common.init;

import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.lib.helper.BlockRegistryHelper;

public class ONIBlocks {
    public static final RegistryObject<ONIBaseRock> IgneousRock = BlockRegistryHelper.register("igneous_rock", () -> new ONIBaseRock(1, 2, 6));

    public static void register(){}
}
