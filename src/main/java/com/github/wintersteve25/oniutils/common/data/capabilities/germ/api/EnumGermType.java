package com.github.wintersteve25.oniutils.common.data.capabilities.germ.api;

/**
 * Enum that stores the different types of germs
 */
public enum EnumGermType {
    NOTHING("no_germs"),
    SLIME_LUNG("slime_lungs"),
    FLORAL_SCENTS("floral_scents"),
    FOOD_POISON("food_poisoning"),
    ZOMBIE_SPORES("zombie_spores");

    private final String name;

    EnumGermType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static EnumGermType getGermFromName(String name) {
        for (EnumGermType germTypes : EnumGermType.values()) {
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
