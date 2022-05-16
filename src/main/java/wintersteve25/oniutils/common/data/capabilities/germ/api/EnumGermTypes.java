package wintersteve25.oniutils.common.data.capabilities.germ.api;

/**
 * Enum that stores the different types of germs
 */
public enum EnumGermTypes {
    NOTHING("no_germs"),
    SLIMELUNG("slime_lungs"),
    FLORALSCENTS("floral_scents"),
    FOODPOISON("food_poisoning"),
    ZOMBIESPORES("zombie_spores");

    private final String name;

    EnumGermTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static EnumGermTypes getGermFromName(String name) {
        for (EnumGermTypes germTypes : EnumGermTypes.values()) {
            if (germTypes.getName().equals(name)) {
                return germTypes;
            }
        }
        return null;
    }

    public int getTypeAmounts() {
        return values().length;
    }
}
