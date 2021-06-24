package wintersteve25.oniutils.common.utils.helper;

import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIBlocksHelper {
    public static void register() {
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseRock ndr : ONIBlocks.rocksListNoDataGen) {
            RegistryHelper.register(MiscHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
        for (ONIBaseDirectional b : ONIBlocks.direList) {
            RegistryHelper.register(MiscHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseDirectional ndr : ONIBlocks.direListNoDataGen) {
            RegistryHelper.register(MiscHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
    }
}
