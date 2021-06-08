package wintersteve25.oniutils.common.lib.helper;

import wintersteve25.oniutils.common.blocks.libs.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseRock;
import wintersteve25.oniutils.common.init.ONIBlocks;

public class ONIBlocksHelper {
    public static void register() {
        for (ONIBaseRock b : ONIBlocks.rocksList) {
            RegistryHelper.register(TextHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseRock ndr : ONIBlocks.rocksListNoDataGen) {
            RegistryHelper.register(TextHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
        for (ONIBaseDirectional b : ONIBlocks.direList) {
            RegistryHelper.register(TextHelper.langToReg(b.getRegName()), () -> b);
        }
        for (ONIBaseDirectional ndr : ONIBlocks.direListNoDataGen) {
            RegistryHelper.register(TextHelper.langToReg(ndr.getRegName()), () -> ndr);
        }
    }
}
