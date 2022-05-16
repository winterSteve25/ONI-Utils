package wintersteve25.oniutils.common.registration.block;

import wintersteve25.oniutils.common.registration.item.ONIItemRegistryData;

public class ONIBlockRegistryData extends ONIItemRegistryData {
    private final boolean doStateGen;
    private final boolean doLootTableGen;

    public ONIBlockRegistryData(boolean doStateGen, boolean doModelGen, boolean doLangGen, boolean doLootTableGen) {
        super(doModelGen, doLangGen);

        this.doStateGen = doStateGen;
        this.doLootTableGen = doLootTableGen;
    }

    public ONIBlockRegistryData() {
        this(false, true, true, true);
    }

    public boolean isDoStateGen() {
        return doStateGen;
    }

    public boolean isDoLootTableGen() {
        return doLootTableGen;
    }
}
