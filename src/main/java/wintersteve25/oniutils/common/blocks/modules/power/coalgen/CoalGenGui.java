package wintersteve25.oniutils.common.blocks.modules.power.coalgen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.libs.ONIBaseGui;

public class CoalGenGui extends ONIBaseGui<CoalGenContainer> {

    private static ResourceLocation bg = new ResourceLocation(ONIUtils.MODID, "textures/gui/machines/coal_gen_gui.png");

    public CoalGenGui(CoalGenContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name, bg);
    }

}
