package wintersteve25.oniutils.common.registration.item;

public class ONIItemRegistryData {
    private final boolean doModelGen;
    private final boolean doLangGen;

    public ONIItemRegistryData(boolean doModelGen, boolean doLangGen) {
        this.doModelGen = doModelGen;
        this.doLangGen = doLangGen;
    }

    public boolean isDoModelGen() {
        return doModelGen;
    }

    public boolean isDoLangGen() {
        return doLangGen;
    }
}
