package wintersteve25.oniutils.common.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ONIConfig {
    public static final String CAT_GERM = "germs";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLE_GERM;

    static {
        ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();
        SERVER_CONFIG = SERVERBUILDER.build();

        SERVERBUILDER.comment("Germs System Settings").push(CAT_GERM);
        ENABLE_GERM = SERVERBUILDER.comment("Enable Germs").define("enableGerms", true);

        SERVERBUILDER.pop();
    }
}
