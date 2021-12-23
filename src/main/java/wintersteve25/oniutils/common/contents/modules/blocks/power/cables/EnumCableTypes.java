package wintersteve25.oniutils.common.contents.modules.blocks.power.cables;

import net.minecraftforge.common.IExtensibleEnum;

public enum EnumCableTypes implements IExtensibleEnum {
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

    public static EnumCableTypes create(String name, String wireName) {
        throw new IllegalArgumentException("Enum not extended");
    }
}
