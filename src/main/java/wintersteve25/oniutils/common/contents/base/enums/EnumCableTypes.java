package wintersteve25.oniutils.common.contents.base.enums;

public enum EnumCableTypes {
    WIRE("Wire", 1000),
    CONDUCTIVE("Conductive Wire", 2000),
    HEAVIWATTS("Heavi-Watts Wire", 20000);

    private final String name;
    private final int powerTransferLimit;

    EnumCableTypes(String name, int powerTransferLimit) {
        this.name = name;
        this.powerTransferLimit = powerTransferLimit;
    }

    public String getName() {
        return name;
    }

    public int getPowerTransferLimit() {
        return powerTransferLimit;
    }
}
