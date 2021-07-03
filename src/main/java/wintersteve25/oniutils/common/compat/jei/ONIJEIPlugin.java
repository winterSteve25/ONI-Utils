package wintersteve25.oniutils.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;
import wintersteve25.oniutils.ONIUtils;

@JeiPlugin
public class ONIJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ONIUtils.MODID, "jei_plugin");
    }
}