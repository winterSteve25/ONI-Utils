package wintersteve25.oniutils.common.capability.germ.api;

/**
 * Enum that stores the different types of germs
 */
public enum EnumGermTypes {
    NOTHING("No Germs"),
    SLIMELUNG("Slime Lungs"),
    FLORALSCENTS("Floral Scents"),
    FOODPOISON("Food Poisoning"),
    ZOMBIESPORES("Zombie Spores");

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
