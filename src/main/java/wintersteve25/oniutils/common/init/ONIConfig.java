package wintersteve25.oniutils.common.init;

import net.minecraftforge.common.ForgeConfigSpec;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class ONIConfig {
    public static final String CAT_GERM = "germs";
//    public static final String CAT_POTR = "potr";
    public static final String CAT_TRAITS = "traits";
    public static final String CAT_MACHINE = "machines";
    public static final String CAT_TEMP = "temperature";
    public static final String CAT_GAS = "gas";
    public static final String CAT_MORALE = "morale";
    public static final String CAT_MISC = "misc";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.BooleanValue ENABLE_GERMS;
    public static ForgeConfigSpec.IntValue GERM_DUP_SPEED;
    public static ForgeConfigSpec.IntValue GERM_DUP_SPEED_PLAYER;
    public static ForgeConfigSpec.IntValue GERM_STOP_DUP_AMOUNT;

//    public static ForgeConfigSpec.BooleanValue ENABLE_POTR;
//    public static ForgeConfigSpec.IntValue PLAYER_EMIT_SPEED;
//    public static ForgeConfigSpec.IntValue PLAYER_BREATH_TIMER;

    public static ForgeConfigSpec.BooleanValue ENABLE_TRAITS;

    public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT;
    public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT_SPEED;
    public static ForgeConfigSpec.IntValue MANUAL_GEN_PROCESS_TIME;

    public static ForgeConfigSpec.IntValue COAL_GEN_PLASMA_OUTPUT;
    public static ForgeConfigSpec.IntValue COAL_GEN_PROCESS_TIME;

    public static ForgeConfigSpec.IntValue ALGAE_DIFFUSER_PLASMA_INPUT;
    public static ForgeConfigSpec.IntValue ALGAE_DIFFUSER_PROCESS_TIME;

    public static ForgeConfigSpec.BooleanValue ENABLE_TEMPERATURE;

    public static ForgeConfigSpec.BooleanValue ENABLE_GAS;
    public static ForgeConfigSpec.DoubleValue PLAYER_BREATH_AMOUNT;
    public static ForgeConfigSpec.IntValue PLAYER_REQUIRED_OXYGEN_AMOUNT;

    public static ForgeConfigSpec.BooleanValue ENABLE_MORALE;

    static {
        ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();

        SERVERBUILDER.comment("Germs System Settings").push(CAT_GERM);
        ENABLE_GERMS = SERVERBUILDER.comment("Should the germ system be enabled?").define("enableGerms", true);
        GERM_DUP_SPEED = SERVERBUILDER.comment("How many ticks should germs naturally increase (Blocks)").defineInRange("germDupSpeed", 20, 1, MiscHelper.INT_MAX);
        GERM_DUP_SPEED_PLAYER = SERVERBUILDER.comment("How many ticks should germs naturally increase (Players)").defineInRange("germDupSpeedPlayer", 2000, 1, MiscHelper.INT_MAX);
        GERM_STOP_DUP_AMOUNT = SERVERBUILDER.comment("How many germs should a block/player have before germs stop naturally increasing?").defineInRange("germDupLimit", 10000000, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

//        SERVERBUILDER.comment("Pollution of the Realms compat Settings").push(CAT_POTR);
//        ENABLE_POTR = SERVERBUILDER.comment("Should the gas addon be enabled?").define("enableGas", false);
//        PLAYER_EMIT_SPEED = SERVERBUILDER.comment("How fast should player produce carbon dioxide?").defineInRange("playerEmitCO2Speed", 2000, 1, MiscHelper.INT_MAX);
//        PLAYER_BREATH_TIMER = SERVERBUILDER.comment("How long should player be able to stay outside of oxygen").defineInRange("playerBreathTimer", 100, 1, MiscHelper.INT_MAX);
//        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Trait Settings").push(CAT_TRAITS);
        ENABLE_TRAITS = SERVERBUILDER.comment("Should the trait system be enabled?").define("enableTrait", true);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Machines Settings").push(CAT_MACHINE);
        MANUAL_GEN_PLASMA_OUTPUT = SERVERBUILDER.comment("How much plasma should manual generator generate per tick").defineInRange("manualGenPlasmaPerTick", 400, 1, MiscHelper.INT_MAX);
        MANUAL_GEN_PLASMA_OUTPUT_SPEED = SERVERBUILDER.comment("How much plasma should manual generator generate per tick when player has speed effect").defineInRange("manualGenPlasmaPerTickSpeed", 600, 1, MiscHelper.INT_MAX);
        MANUAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should manual generator generates power?").defineInRange("manualGenProgressSpeed", 5, 1, MiscHelper.INT_MAX);

        COAL_GEN_PLASMA_OUTPUT = SERVERBUILDER.comment("How much plasma should manual generator generate per tick").defineInRange("coalGenPlasmaPerTick", 40, 1, MiscHelper.INT_MAX);
        COAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should manual generator consume coal").defineInRange("coalGenConsumeSpeed", 80, 1, MiscHelper.INT_MAX);

        ALGAE_DIFFUSER_PLASMA_INPUT = SERVERBUILDER.comment("How much plasma should algae diffuser take per tick").defineInRange("algaeDiffuserPlasmaPerTick", 15, 1, MiscHelper.INT_MAX);
        ALGAE_DIFFUSER_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should algae diffuser consume algae").defineInRange("algaeDiffuserConsumeSpeed", 200, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Temperature Settings").push(CAT_TEMP);
        ENABLE_TEMPERATURE = SERVERBUILDER.comment("Should the temperature system be enabled?").define("enableTemperature", true);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Gas Settings").push(CAT_GAS);
        ENABLE_GAS = SERVERBUILDER.comment("Should the gas system be enabled?").define("enableGas", true);
        PLAYER_BREATH_AMOUNT = SERVERBUILDER.comment("How much grams of gas should player breath in per second in total?").defineInRange("playerBreathAmount", 1D, 1, MiscHelper.INT_MAX);
        PLAYER_REQUIRED_OXYGEN_AMOUNT = SERVERBUILDER.comment("How much grams of oxygen should player consume every second").defineInRange("playerConsumeAmount", 2, 1, MiscHelper.INT_MAX);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Morale Settings").push(CAT_MORALE);
        ENABLE_MORALE = SERVERBUILDER.comment("Should the morale system be enabled?").define("enableMorale", true);
        SERVERBUILDER.pop();

        SERVERBUILDER.comment("Misc").push(CAT_MISC);
        SERVERBUILDER.pop();

        SERVER_CONFIG = SERVERBUILDER.build();
    }
}
