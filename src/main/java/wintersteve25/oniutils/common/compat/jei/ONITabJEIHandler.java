package wintersteve25.oniutils.common.compat.jei;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rectangle2d;
import wintersteve25.oniutils.client.gui.ONIBaseGuiContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ONITabJEIHandler implements IGuiContainerHandler<ONIBaseGuiContainer<?>> {
    @Override
    public List<Rectangle2d> getGuiExtraAreas(ONIBaseGuiContainer<?> containerScreen) {
        return containerScreen.isTabOpen() ? Arrays.asList(new Rectangle2d((containerScreen.width - 130 - 200) / 2, containerScreen.currentTab.getY(), 147, 167)) : Collections.emptyList();
    }
}
