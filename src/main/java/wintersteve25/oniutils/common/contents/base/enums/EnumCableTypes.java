package wintersteve25.oniutils.common.contents.base.enums;

public enum EnumCableTypes {
    WIRE("wire", 1000),
    CONDUCTIVE("conductive_wire", 2000),
    HEAVIWATTS("heavi_watts_wire", 20000);

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
