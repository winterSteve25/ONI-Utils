package wintersteve25.oniutils.common.registries;

import net.minecraftforge.common.ForgeConfigSpec;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class ONIConfig {

    public static class Server {
        public static final String CAT_GERM = "germs";
        public static final String CAT_TRAITS = "traits";
        public static final String CAT_MACHINE = "machines";
        public static final String CAT_GAS = "gas";
        public static final String CAT_MORALE = "morale";
        public static final String CAT_MISC = "misc";

        public static ForgeConfigSpec SERVER_CONFIG;

        public static ForgeConfigSpec.BooleanValue ENABLE_GERMS;
        public static ForgeConfigSpec.IntValue GERM_DUP_SPEED;
        public static ForgeConfigSpec.IntValue GERM_DUP_SPEED_PLAYER;
        public static ForgeConfigSpec.IntValue GERM_STOP_DUP_AMOUNT;

        public static ForgeConfigSpec.BooleanValue ENABLE_TRAITS;

        public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT;
        public static ForgeConfigSpec.IntValue MANUAL_GEN_PLASMA_OUTPUT_SPEED;
        public static ForgeConfigSpec.IntValue MANUAL_GEN_PROCESS_TIME;

        public static ForgeConfigSpec.IntValue COAL_GEN_POWER_PRODUCE;
        public static ForgeConfigSpec.IntValue COAL_GEN_PROCESS_TIME;

        public static ForgeConfigSpec.IntValue ALGAE_DIFFUSER_PLASMA_INPUT;
        public static ForgeConfigSpec.IntValue ALGAE_DIFFUSER_PROCESS_TIME;

        public static ForgeConfigSpec.IntValue OXYLITE_COOLDOWN;
        public static ForgeConfigSpec.IntValue GAS_UPDATE_FREQUENCY;

        public static ForgeConfigSpec.BooleanValue ENABLE_MORALE;
        public static ForgeConfigSpec.IntValue MORALE_FOR_STRESS;

        static {
            ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();

            SERVERBUILDER.comment("Germs System Settings").push(CAT_GERM);
            ENABLE_GERMS = SERVERBUILDER.comment("Should the germ system be enabled?").define("enableGerms", true);
            GERM_DUP_SPEED = SERVERBUILDER.comment("How many ticks should germs naturally increase (Blocks)").defineInRange("germDupSpeed", 20, 1, MiscHelper.INT_MAX);
            GERM_DUP_SPEED_PLAYER = SERVERBUILDER.comment("How many ticks should germs naturally increase (Players)").defineInRange("germDupSpeedPlayer", 2000, 1, MiscHelper.INT_MAX);
            GERM_STOP_DUP_AMOUNT = SERVERBUILDER.comment("How many germs should a block/player have before germs stop naturally increasing?").defineInRange("germDupLimit", 10000000, 1, MiscHelper.INT_MAX);
            SERVERBUILDER.pop();

            SERVERBUILDER.comment("Trait Settings").push(CAT_TRAITS);
            ENABLE_TRAITS = SERVERBUILDER.comment("Should the trait system be enabled?").define("enableTrait", true);
            SERVERBUILDER.pop();

            SERVERBUILDER.comment("Machines Settings").push(CAT_MACHINE);
            MANUAL_GEN_PLASMA_OUTPUT = SERVERBUILDER.comment("How much plasma should manual generator generate per tick").defineInRange("manualGenPlasmaPerTick", 400, 1, MiscHelper.INT_MAX);
            MANUAL_GEN_PLASMA_OUTPUT_SPEED = SERVERBUILDER.comment("How much plasma should manual generator generate per tick when player has speed effect").defineInRange("manualGenPlasmaPerTickSpeed", 600, 1, MiscHelper.INT_MAX);
            MANUAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should manual generator generates power?").defineInRange("manualGenProgressSpeed", 5, 1, MiscHelper.INT_MAX);

            COAL_GEN_POWER_PRODUCE = SERVERBUILDER.comment("How much plasma should each coal generate in the coal generator").defineInRange("coalGenPowerProduction", 2000, 1, MiscHelper.INT_MAX);
            COAL_GEN_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should manual generator consume coal").defineInRange("coalGenConsumeSpeed", 800, 1, MiscHelper.INT_MAX);

            ALGAE_DIFFUSER_PLASMA_INPUT = SERVERBUILDER.comment("How much plasma should algae diffuser take per tick").defineInRange("algaeDiffuserPlasmaPerTick", 15, 1, MiscHelper.INT_MAX);
            ALGAE_DIFFUSER_PROCESS_TIME = SERVERBUILDER.comment("Every how many ticks should algae diffuser consume algae").defineInRange("algaeDiffuserConsumeSpeed", 200, 1, MiscHelper.INT_MAX);
            SERVERBUILDER.pop();

            SERVERBUILDER.comment("Gas Settings").push(CAT_GAS);
            OXYLITE_COOLDOWN = SERVERBUILDER.comment("How many ticks should oxylite take in-between oxygen production?").defineInRange("oxyliteCooldown", 120, 1, MiscHelper.INT_MAX);
            GAS_UPDATE_FREQUENCY = SERVERBUILDER.comment("How often (ticks) should the gas data in each chunk update? (Gas Spread, etc) (Smaller the faster, faster it is, more performance it takes.)").defineInRange("gasUpdateFrequency", 40, 1, MiscHelper.INT_MAX);
            SERVERBUILDER.pop();

            SERVERBUILDER.comment("Morale Settings").push(CAT_MORALE);
            ENABLE_MORALE = SERVERBUILDER.comment("Should the morale system be enabled?").define("enableMorale", true);
            MORALE_FOR_STRESS = SERVERBUILDER.comment("Below how many morale should players start stressing").defineInRange("moraleTillStress", 15, 1, MiscHelper.INT_MAX);
            SERVERBUILDER.pop();

            SERVERBUILDER.comment("Misc").push(CAT_MISC);
            SERVERBUILDER.pop();

            SERVER_CONFIG = SERVERBUILDER.build();
        }
    }

    public static class Client {
    }
}
