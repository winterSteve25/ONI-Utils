package wintersteve25.oniutils.common.capability.gas.api;

import net.minecraftforge.common.IExtensibleEnum;
import wintersteve25.oniutils.common.utils.MiscHelper;

public enum EnumGasTypes implements IExtensibleEnum {
    //Blindness when oxygen is below 5%
    VACUUM("Vacuum"),
    //Oxygenated when above 5%
    OXYGEN("Oxygen"),
    //Hunger when above 20%, Poison when above 40%, Damage player when above 95%
    POLLUTED_OXYGEN("Polluted Oxygen"),
    //Weakness when above 20%, Poison when above 30%, Damage player when above 95%
    CO2("Carbon Dioxide"),
    //Kill germs within chunk when above 5%, Weakness when above 30%, Damage player when above 95%
    CHLORINE("Chlorine"),
    //Slowness when above 20%, Weakness when above 30%, Damage player when above 95%
    HYDROGEN("Hydrogen"),
    //Weakness when above 20%, Poison when above 40%, Damage player when above 95%
    SOUR_GAS("Sour Gas"),
    //Oxygenated when below 5%, Weakness when above 5%, Damage player when above 95%
    STEAM("Steam"),
    //Weakness when above 10%, Poison when above 25%, Wither when above 40%, Damage player when above 95%
    ROCK_GAS("Rock Gas"),
    //Weakness when above 10%, Mining Fatigue when above 25%, Damage player when above 95%
    HELIUM("Helium"),
    //Slowness when above 15%, Mining Fatigue when above 30%, Damage player when above 95%
    CARBON("Carbon Gas"),
    //Weakness when above 15%, Poison when above 30%, Damage player when above 95%
    NATURAL_GAS("Natural Gas"),
    //Slowness when above 15%, Mining Fatigue when above 30%, Damage player when above 95%
    PHOSPHORUS("Phosphorus Gas"),
    //Weakness when above 15%, Poison when above 30%, Damage player when above 95%
    SULFUR("Sulfur Gas"),
    //Weakness when above 15%, Poison when above 30%, Damage player when above 95%
    PROPANE("Propane Gas"),
    //Slowness when above 15%, Mining Fatigue when above 30%, Damage player when above 95%
    ETHANOL("Ethanol Gas"),
    //Slowness when above 15%, Mining Fatigue when above 30%, Damage player when above 95%
    SALT_GAS("Salt Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    STEEL("Steel Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    IRON("Iron Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    COPPER("Copper Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    ALUMINUM("Aluminum Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    NIOBIUM("Niobium Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    TUNGSTEN("Tungsten Gas"),
    //Slowness when above 15%, Mining Fatigue when above 30%, Damage player when above 95%
    SUPER_COOLANT("Super Coolant"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    GOLD("Gold Gas"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    MERCURY("Mercury Gas"),
    //Mining Fatigue when above 5%, Poison when above 10%, Damage player when above 15%
    NUCLEAR_FALLOUT("Nuclear Waste"),
    //Mining Fatigue when above 15%, Poison when above 30%, Damage player when above 95%
    COBALT("Cobalt Gas");

    private String name;

    EnumGasTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getTypeSize() {
        return values().length;
    }

    public static EnumGasTypes getGasFromName(String name) {
        for (EnumGasTypes gas : EnumGasTypes.values()) {
            if (MiscHelper.langToReg(name).equals(MiscHelper.langToReg(gas.getName()))) {
                return gas;
            }
        }
        return null;
    }

    public static EnumGasTypes create(String name, String gasName) {
        throw new IllegalArgumentException("Enum not extended");
    }
}
