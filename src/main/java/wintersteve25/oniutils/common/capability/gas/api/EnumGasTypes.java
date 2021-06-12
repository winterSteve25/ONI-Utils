package wintersteve25.oniutils.common.capability.gas.api;

public enum EnumGasTypes {
    VACUUM("Vacuum"),
    OXYGEN("Oxygen"),
    POLLUTEDOXYGEN("Polluted_Oxygen"),
    CHLORINE("Chlorine"),
    CARBONDIOXIDE("Carbon_Dioxide");

    private final String name;

    EnumGasTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static EnumGasTypes getGasFromName(String name) {
        for (EnumGasTypes gasTypes : EnumGasTypes.values()) {
            if (gasTypes.getName().equals(name)) {
                return gasTypes;
            }
        }
        return null;
    }

    public int getTypeAmounts() {
        return values().length;
    }
}
