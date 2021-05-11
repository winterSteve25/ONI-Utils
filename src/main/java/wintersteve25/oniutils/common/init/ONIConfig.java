package wintersteve25.oniutils.common.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class ONIConfig {
    public static final String CAT_GERM = "germs";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLE_GERM;
    public static ForgeConfigSpec.BooleanValue ENABLE_SLIMELUNG;
    public static ForgeConfigSpec.BooleanValue ENABLE_FLORALSCENTS;
    public static ForgeConfigSpec.BooleanValue ENABLE_FOODPOISON;
    public static ForgeConfigSpec.BooleanValue ENABLE_ZOMBIESPORES;

    static {
        ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();
        SERVER_CONFIG = SERVERBUILDER.build();

        SERVERBUILDER.comment("Germs System Settings").push(CAT_GERM);
        ENABLE_GERM = SERVERBUILDER.comment("Enable Germs").define("enableGerms", true);
        ENABLE_SLIMELUNG = SERVERBUILDER.comment("Enable Slime Lung, only avaliable if enableGerms is true").define("enableSlimeLung", true);
        ENABLE_FLORALSCENTS = SERVERBUILDER.comment("Enable Floral Scents, only avaliable if enableGerms is true").define("enableFloraScents", true);
        ENABLE_FOODPOISON = SERVERBUILDER.comment("Enable Food Poison, only avaliable if enableGerms is true").define("enableFoodPoison", true);
        ENABLE_ZOMBIESPORES = SERVERBUILDER.comment("Enable Zombie Spores, only avaliable if enableGerms is true").define("enableZombieSpores", true);

        SERVERBUILDER.pop();
    }
}
