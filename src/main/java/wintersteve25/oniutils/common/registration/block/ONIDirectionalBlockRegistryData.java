package wintersteve25.oniutils.common.registration.block;

import net.minecraftforge.client.model.generators.ModelFile;

public class ONIDirectionalBlockRegistryData extends ONIBlockRegistryData {
    private final int angleOffset;
    private final ModelFile modelFile;

    public ONIDirectionalBlockRegistryData(boolean doStateGen, boolean doModelGen, boolean doLangGen, boolean doLootTableGen, int angleOffset, ModelFile modelFile) {
        super(doStateGen, doModelGen, doLangGen, doLootTableGen);
        this.angleOffset = angleOffset;
        this.modelFile = modelFile;
    }

    public ONIDirectionalBlockRegistryData(int angleOffset, ModelFile modelFile) {
        this(false, true, true, true, angleOffset, modelFile);
    }

    public ONIDirectionalBlockRegistryData() {
        this.angleOffset = 0;
        this.modelFile = null;
    }

    public int getAngleOffset() {
        return angleOffset;
    }

    public ModelFile getModelFile() {
        return modelFile;
    }
}
