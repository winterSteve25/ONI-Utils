package wintersteve25.oniutils.common.init;

import net.minecraftforge.common.ForgeConfigSpec;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class ONIConfig {
    public static final String CAT_GERM = "germs";
    public static final String CAT_GAS = "gas";
    public static final String CAT_TRAITS = "traits";
    public static final String CAT_POWER = "plasma";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLE_GERMS;
    public static ForgeConfigSpec.IntValue GERM_DUP_SPEED;
    public static ForgeConfigSpec.IntValue GERM_DUP_SPEED_PLAYER;
    public static ForgeConfigSpec.IntValue GERM_STOP_DUP_AMOUNT;

    public static ForgeConfigSpec.BooleanValue ENABLE_GAS;
    public static ForgeConfigSpec.IntValue PLAYER_EMIT_SPEED;
    public static ForgeConfigSpec.IntValue PLAYER_BREATH_TIMER;

    public static ForgeConfigSpec.BooleanValue ENABLE_TRAITS;

    public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT;
    public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT_SPEED;
    public static ForgeConfigSpec.IntValue MANUAL_GEN_PROCESS_TIME;

    public static ForgeConfigSpec.IntValue COAL_GEN_PLASMA_OUTPUT;
    public static ForgeConfigSpec.IntValue COAL_GEN_PROCESS_TIME;

    static {
        ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();

        SERVERBUILDER.comment("Germs System Settings").push(CAT_GERM);
        ENABLE_GERMS = SERVERBUILDER.comment("Should the germ system be enabled?").define("enableGerms", true);
        GERM_DUP_SPEED = SERVERBUILDER.comment("How many ticks should germs naturally increase (Blocks)").defineInRange("germDupSpeed", 20, 1, MiscHelper.INT_MAX);
        GERM_DUP_SPEED_PLAYER = SERVERBUILDER.comment("How many ticks should germs naturally increase (Players)").defineInRange("germDupSpeedPlayer", 2000, 1, MiscHelper.INT_MAX);
        GERM_STOP_DUP_AMOUNT = SERVERBUILDER.comment("How many germs should a block/player have before germs stop naturally increasing?").defineInRange("germDupLimit", 10000000, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Gas System (Pollution of the Realms addon) Settings").push(CAT_GAS);
        ENABLE_GAS = SERVERBUILDER.comment("Should the gas addon be enabled?").define("enableGas", false);
        PLAYER_EMIT_SPEED = SERVERBUILDER.comment("How fast should player produce carbon dioxide?").defineInRange("playerEmitCO2Speed", 2000, 1, MiscHelper.INT_MAX);
        PLAYER_BREATH_TIMER = SERVERBUILDER.comment("How long should player be able to stay outside of oxygen").defineInRange("playerBreathTimer", 100, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Trait Settings").push(CAT_TRAITS);
        ENABLE_TRAITS = SERVERBUILDER.comment("Should the trait system be enabled?").define("enableTrait", true);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Plasma settings").push(CAT_POWER);
        MANUAL_GEN_PLASMA_OUTPUT = SERVERBUILDER.comment("How much plasma should manual generator generate per tick").defineInRange("manualGenPlasmaPerTick", 400, 1, MiscHelper.INT_MAX);
        MANUAL_GEN_PLASMA_OUTPUT_SPEED = SERVERBUILDER.comment("How much plasma should manual generator generate per tick when player has speed effect").defineInRange("manualGenPlasmaPerTickSpeed", 600, 1, MiscHelper.INT_MAX);
        MANUAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("How many every ticks should manual generator generates power?").defineInRange("manualGenProgressSpeed", 20, 1, MiscHelper.INT_MAX);

        COAL_GEN_PLASMA_OUTPUT = SERVERBUILDER.comment("How much plasma should manual generator generate per tick").defineInRange("coalGenPlasmaPerTick", 600, 1, MiscHelper.INT_MAX);
        COAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("How many every ticks should manual generator generates power?").defineInRange("coalGenProgressSpeed", 20, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

        SERVER_CONFIG = SERVERBUILDER.build();
    }
}
