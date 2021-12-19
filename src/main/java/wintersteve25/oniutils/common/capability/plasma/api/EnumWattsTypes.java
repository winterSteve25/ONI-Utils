package wintersteve25.oniutils.common.capability.plasma.api;

import wintersteve25.oniutils.common.contents.modules.power.cables.EnumCableTypes;

public enum EnumWattsTypes {
    LOW("low", 1000, EnumCableTypes.WIRE),
    MEDIUM("medium", 2000, EnumCableTypes.CONDUCTIVE),
    HIGH("high", 20000, EnumCableTypes.HEAVIWATTS);

    private String name;
    private int powerMax;
    private EnumCableTypes cableType;

    EnumWattsTypes(String name, int powerMax, EnumCableTypes cableType) {
        this.name = name;
        this.powerMax = powerMax;
        this.cableType = cableType;
    }

    public String getName() {
        return name;
    }

    public int getPowerMax() {
        return powerMax;
    }

    public EnumCableTypes getCableType() {
        return cableType;
    }

    public static EnumWattsTypes getWattsFromName(String name) {
        for (EnumWattsTypes wattsTypes : EnumWattsTypes.values()) {
            if (wattsTypes.getName().equals(name)) {
                return wattsTypes;
            }
        }
        return null;
    }
}
