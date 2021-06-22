package wintersteve25.oniutils.common.blocks.modules.power.cables;

public enum EnumCableTypes {
    WIRE("wire"),
    CONDUCTIVE("conductive"),
    HEAVIWATTS("heavi_watts");

    private String name;

    EnumCableTypes(String name) {
        this.name = name;
    }
}
