package wintersteve25.oniutils.common.compat.jei;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ONITabJEIHandler implements IGuiContainerHandler<ONIBaseGuiContainer<?>> {
    @Override
    public List<Rect2i> getGuiExtraAreas(ONIBaseGuiContainer<?> containerScreen) {
        return containerScreen.isTabOpen() ? Arrays.asList(new Rect2i((containerScreen.width - 130 - 200) / 2, containerScreen.currentTab.getY(), 147, 167)) : Collections.emptyList();
    }
}
