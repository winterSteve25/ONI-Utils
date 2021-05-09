package wintersteve25.oniutils.common.init;

import net.minecraftforge.fml.RegistryObject;
import wintersteve25.oniutils.common.blocks.ONIBaseRock;
import wintersteve25.oniutils.common.lib.helper.BlockRegistryHelper;

import java.util.ArrayList;
import java.util.List;

public class ONIBlocks {
    public static final RegistryObject<ONIBaseRock> IgneousRock = BlockRegistryHelper.register("igneous_rock", () -> new ONIBaseRock(1, 2, 6));

    public static final ONIBaseRock SedimentaryRock = new ONIBaseRock(1, 4, 10, "Sedimentary Rock");

    public static List<ONIBaseRock> rocksList = new ArrayList<>();

    static {
        SedimentaryRock.initBlock(SedimentaryRock);
    }

    public static void register(){}
}
