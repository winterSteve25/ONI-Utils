package wintersteve25.oniutils.common.contents.base.enums;

public enum EnumCableTypes {
    WIRE("Wire"),
    CONDUCTIVE("Conductive Wire"),
    HEAVIWATTS("Heavi-Watts Wire");

    private final String name;

    EnumCableTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
