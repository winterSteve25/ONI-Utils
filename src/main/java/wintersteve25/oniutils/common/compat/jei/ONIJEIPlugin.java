package wintersteve25.oniutils.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;

@JeiPlugin
public class ONIJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ONIUtils.MODID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
       registration.addGenericGuiContainerHandler(ONIBaseGuiContainer.class, new ONITabJEIHandler());
    }
}