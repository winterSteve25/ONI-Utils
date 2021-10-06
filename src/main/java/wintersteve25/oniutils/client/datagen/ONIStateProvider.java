package wintersteve25.oniutils.client.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.MiscHelper;
import wintersteve25.oniutils.common.utils.ModelFileHelper;
import wintersteve25.oniutils.common.utils.ResoureceLocationHelper;

public class ONIStateProvider extends BlockStateProvider {

    public ONIStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ONIUtils.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        autoGenStatesAndModels();
        simpleBlock(ONIBlocks.Slime);
    }

    private void autoGenStatesAndModels() {
        for (ONIBaseBlock b : ONIBlocks.blockList.keySet()) {
            if (b != null) {
                simpleBlock(b);
            }
        }

        for (ONIBaseDirectional b : ONIBlocks.directionalList.keySet()) {
            if (b != null) {
                if (b.getAngelOffset() == 0) {
                    directionalBlock(b, b.getModelFile());
                } else {
                    directionalBlock(b, b.getModelFile(), b.getAngelOffset());
                }
            }
        }

        weightedRock(ONIBlocks.Abyssalite, 2);
        weightedRock(ONIBlocks.BleachStone, 8);
        weightedRock(ONIBlocks.Fertilizer, 1);
        weightedRock(ONIBlocks.Fossil, 5);
        weightedRock(ONIBlocks.GoldAmalgam, 8);
        weightedRock(ONIBlocks.Granite, 8);
        weightedRock(ONIBlocks.IgneousRock, 8);
        weightedRock(ONIBlocks.MaficRock, 8);
        weightedRock(ONIBlocks.Neutronium, 8);
        weightedRock(ONIBlocks.Oxylite, 8);
        weightedRock(ONIBlocks.Phosphorite, 8);
        weightedRock(ONIBlocks.PollutedIce, 2);
        weightedRock(ONIBlocks.Regolith, 8);
        weightedRock(ONIBlocks.Rust, 1);
        weightedRock(ONIBlocks.Wolframite, 8);
    }

    private void weightedRock(ONIBaseBlock block, int amoutOfAlts) {
        String name = MiscHelper.langToReg(block.getRegName());
        weightedState(block, name, new ResourceLocation(ONIUtils.MODID, "block/rocks/" + name), amoutOfAlts);
    }

    private void weightedState(Block block, String modelBaseName, ResourceLocation textureBaseLocation, int amountOfAlts) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    switch (amountOfAlts) {
                        case 1:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).build();
                        case 2:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).build();
                        case 3:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).build();
                        case 4:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).build();
                        case 5:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).build();
                        case 6:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt6", ResoureceLocationHelper.extend(textureBaseLocation, "_alt6"), models())).weight((int) 100 / amountOfAlts).build();
                        case 7:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt6", ResoureceLocationHelper.extend(textureBaseLocation, "_alt6"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt7", ResoureceLocationHelper.extend(textureBaseLocation, "_alt7"), models())).weight((int) 100 / amountOfAlts).build();
                        case 8:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt6", ResoureceLocationHelper.extend(textureBaseLocation, "_alt6"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt7", ResoureceLocationHelper.extend(textureBaseLocation, "_alt7"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt8", ResoureceLocationHelper.extend(textureBaseLocation, "_alt8"), models())).weight((int) 100 / amountOfAlts).build();
                        case 9:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt6", ResoureceLocationHelper.extend(textureBaseLocation, "_alt6"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt7", ResoureceLocationHelper.extend(textureBaseLocation, "_alt7"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt8", ResoureceLocationHelper.extend(textureBaseLocation, "_alt8"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt9", ResoureceLocationHelper.extend(textureBaseLocation, "_alt9"), models())).weight((int) 100 / amountOfAlts).build();
                        case 10:
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt", ResoureceLocationHelper.extend(textureBaseLocation, "_alt"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt2", ResoureceLocationHelper.extend(textureBaseLocation, "_alt2"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt3", ResoureceLocationHelper.extend(textureBaseLocation, "_alt3"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt4", ResoureceLocationHelper.extend(textureBaseLocation, "_alt4"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt5", ResoureceLocationHelper.extend(textureBaseLocation, "_alt5"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt6", ResoureceLocationHelper.extend(textureBaseLocation, "_alt6"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt7", ResoureceLocationHelper.extend(textureBaseLocation, "_alt7"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt8", ResoureceLocationHelper.extend(textureBaseLocation, "_alt8"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt9", ResoureceLocationHelper.extend(textureBaseLocation, "_alt9"), models())).weight((int) 100 / amountOfAlts).nextModel()
                                    .modelFile(ModelFileHelper.cubeAll(modelBaseName + "_alt9", ResoureceLocationHelper.extend(textureBaseLocation, "_alt10"), models())).weight((int) 100 / amountOfAlts).build();
                        default:
                            ONIUtils.LOGGER.warn("Tried to create weighted state out of supported range");
                            return ConfiguredModel.builder().modelFile(ModelFileHelper.cubeAll(modelBaseName, textureBaseLocation, models())).weight((int) 100 / amountOfAlts).build();
                    }
                });
    }
}
