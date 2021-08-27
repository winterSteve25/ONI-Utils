package wintersteve25.oniutils.common.utils;

import wintersteve25.oniutils.client.renderers.geckolibs.base.GeckolibModelBase;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.coal.CoalGenTE;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenItemBlock;
import wintersteve25.oniutils.common.blocks.modules.power.manual.ManualGenTE;

public final class ONIConstants {
    public static final class Geo {
        public static final GeckolibModelBase<CoalGenTE> COAL_GEN_TE = new GeckolibModelBase<>("machines/power/coal_gen.geo.json", "machines/power/coal_gen.png", "machines/power/coal_gen.animation.json");
        public static final GeckolibModelBase<CoalGenItemBlock> COAL_GEN_IB = new GeckolibModelBase<>("machines/power/coal_gen.geo.json", "machines/power/coal_gen.png", "machines/power/coal_gen.animation.json");
        public static final GeckolibModelBase<ManualGenTE> MANUAL_GEN_TE = new GeckolibModelBase<>("machines/power/manual_gen.geo.json", "machines/power/manual_gen.png", "machines/power/manual_gen.animation.json");
        public static final GeckolibModelBase<ManualGenItemBlock> MANUAL_GEN_IB = new GeckolibModelBase<>("machines/power/manual_gen.geo.json", "machines/power/manual_gen.png", "machines/power/manual_gen.animation.json");
    }
    public static final class PacketType {
        public static final byte REDSTONE_INPUT = 0;
        public static final byte REDSTONE_OUTPUT_LOW = 1;
        public static final byte REDSTONE_OUTPUT_HIGH = 2;
    }
}
