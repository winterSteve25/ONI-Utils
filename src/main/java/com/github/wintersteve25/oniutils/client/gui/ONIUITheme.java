package com.github.wintersteve25.oniutils.client.gui;

import com.github.wintersteve25.tau.theme.MinecraftTheme;
import com.github.wintersteve25.tau.utils.Color;
import com.github.wintersteve25.tau.utils.InteractableState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;

public class ONIUITheme extends MinecraftTheme {
    
    public static final ONIUITheme INSTANCE = new ONIUITheme();
    private static final Color BACKGROUND = Color.fromRGBA(10, 10, 10, 140);
    private static final Color BACKGROUND_HOVER = Color.fromRGBA(10, 10, 10, 230);
    
    private ONIUITheme() {
        
    }
    
    @Override
    public void drawButton(PoseStack poseStack, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY, InteractableState interactableState) {
        if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
            Screen.fill(poseStack, x, y, x + width, y + height, BACKGROUND_HOVER.getAARRGGBB());
            return;
        }
        
        Screen.fill(poseStack, x, y, x + width, y + height, BACKGROUND.getAARRGGBB());
    }

    @Override
    public void drawContainer(PoseStack poseStack, int x, int y, int width, int height, float partialTicks, int mouseX, int mouseY) {
        Screen.fill(poseStack, x, y, x + width, y + height, BACKGROUND.getAARRGGBB());
    }

    @Override
    public Color getTextColor() {
        return Color.WHITE;
    }
}
