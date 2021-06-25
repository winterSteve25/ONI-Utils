package wintersteve25.oniutils.common.capability.gas.api;

public enum EnumGasTypes {
    OXYGEN("Oxygen"),
    POLLUTEDOXYGEN("Polluted Oxygen"),
    VACUUM("Vacuum"),
    CO2("Carbon Dioxide"),
    CHLORINE("Chlorine"),
    HYDROGEN("Hydrogen"),
    SOURGAS("Sour Gas"),
    STEAM("Steam"),
    ROCKGAS("Rock Gas");

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
            if (name.equals(gas.getName())) {
                return gas;
            }
        }
        return null;
    }
}
