package com.github.wintersteve25.oniutils.common.utils.helpers;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import org.apache.logging.log4j.LogManager;
import com.github.wintersteve25.oniutils.ONIUtils;

public class ModelFileHelper {
    public static ModelFile createModelFile(ResourceLocation location, boolean exists) {
        return new ModelFile(location) {
            @Override
            protected boolean exists() {
                return exists;
            }
        };
    }

    public static ModelFile createModelFile(ResourceLocation location) {
        return createModelFile(location, true);
    }

    public static ModelFile cubeAll(String modelName, ResourceLocation textureLoc, BlockModelProvider modelProvider) {
        return modelProvider.withExistingParent(modelName, "block/cube_all").texture("all", textureLoc);
    }
}
