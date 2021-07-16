package wintersteve25.oniutils.common.utils.helper;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;

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
        return new ModelFile(location) {
            @Override
            protected boolean exists() {
                return true;
            }
        };
    }
}
