package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint;

import com.github.wintersteve25.tau.components.*;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Pad;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.client.Minecraft;

public class ONIBlueprintGui implements UIComponent {
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        return new Align.Builder()
            .withVertical(LayoutSetting.END)
            .build(new Padding(
                new Pad.Builder().left(2).bottom(2).build(),
                new Row.Builder()
                    .withSpacing(2)
                    .build(
                        new Category("Base"),
                        new Category("Power"))));
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ScreenUIRenderer(new ONIBlueprintGui()));
    }

    private record Category(String categoryName) implements UIComponent {
        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return new Column.Builder()
                .build(
                    new Sized(
                        Size.staticSize(200, 300),
                        new Container.Builder()
                            .withChild(new ListView.Builder()
                                .withSpacing(2)
                                .build())),
                    new Sized(
                        Size.staticSize(80, 20),
                        new Button.Builder()
                            .withOnPress((btn) -> {})
                            .build(new Center(new Text.Builder(categoryName)))));
        }
    }
    
    private record Building() implements UIComponent {
        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return null;
        }
    }
}