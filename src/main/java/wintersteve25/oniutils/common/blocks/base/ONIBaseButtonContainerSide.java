package wintersteve25.oniutils.common.blocks.base;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.ONIUtils;

public abstract class ONIBaseButtonContainerSide extends ONIBaseButton{

    public ONIBaseButtonContainerSide(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ResourceLocation RL) {
        super(x, y, width, height, title, pressedAction, RL);
    }

    public static class AlertButton extends ONIBaseButtonContainerSide {
        public AlertButton(int x, int y, IPressable pressedAction) {
            super(x, y, 16, 16, new TranslationTextComponent(""), pressedAction, new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/alert_button"));
        }
    }
    public static class EnergyButton extends ONIBaseButtonContainerSide {
        public EnergyButton(int x, int y, IPressable pressedAction) {
            super(x, y, 16, 16, new TranslationTextComponent(""), pressedAction, new ResourceLocation(ONIUtils.MODID, "textures/gui/misc/energy_button.png"));
        }
    }
}
