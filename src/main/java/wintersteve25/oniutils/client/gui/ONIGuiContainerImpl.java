package wintersteve25.oniutils.client.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import wintersteve25.oniutils.common.contents.base.ONIBaseContainer;

public abstract class ONIGuiContainerImpl<T extends ONIBaseContainer> extends ONIBaseGuiContainer<T> {
    public ONIGuiContainerImpl(T container, PlayerInventory inv, ITextComponent name, ResourceLocation resourceLocation) {
        super(container, inv, name, resourceLocation);
    }

//    protected abstract
}
