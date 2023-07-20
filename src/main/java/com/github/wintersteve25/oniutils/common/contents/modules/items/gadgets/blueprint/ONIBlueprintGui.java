package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint;

import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipe;
import com.github.wintersteve25.oniutils.common.registries.ONIRecipes;
import com.github.wintersteve25.tau.components.*;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.ItemRenderProvider;
import com.github.wintersteve25.tau.utils.Pad;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ONIBlueprintGui extends DynamicUIComponent {
    
    private static final Lazy<List<BlueprintRecipe>> RECIPES = Lazy.of(() -> Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ONIRecipes.BLUEPRINT_RECIPE_TYPE.get()));
    
    private Set<BlueprintRecipe> recipesShown;
    
    private UIComponent categoryButtons() {
        return new Row.Builder()
            .withSpacing(2)
            .build(
                new Category("Base", RECIPES.get().stream().collect(Collectors.toSet()), recipes -> {
                    recipesShown = recipes;
                    rebuild();
                }),
                new Category("Power", RECIPES.get().stream().collect(Collectors.toSet()), recipes -> {
                    recipesShown = recipes;
                    rebuild();
                }));
    }
    
    @Override
    public UIComponent build(Layout layout, Theme theme) {
        if (recipesShown == null) {
            return new Align.Builder()
                .withVertical(LayoutSetting.END)
                .build(new Padding(
                    new Pad.Builder().left(2).bottom(2).build(),
                    new Column.Builder()
                        .withSpacing(2)
                        .withAlignment(LayoutSetting.START)
                        .build(
                                new Sized(
                                    Size.staticSize(200, 300),
                                    new Container.Builder()
                                        .noBackground()),
                                categoryButtons()
                        )));
        }
        
        return new Align.Builder()
            .withVertical(LayoutSetting.END)
            .build(new Padding(
                new Pad.Builder().left(2).bottom(2).build(),
                new Column.Builder()
                    .withSpacing(2)
                    .withAlignment(LayoutSetting.START)
                    .build(
                        new Sized(
                            Size.staticSize(200, 300),
                            new Container.Builder()
                                .withChild(new ListView.Builder()
                                    .withSpacing(2)
                                    .build(recipesShown.stream().map(Building::new).collect(Collectors.toUnmodifiableList())))),
                        categoryButtons()
                    )));
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ScreenUIRenderer(new ONIBlueprintGui()));
    }

    private record Category(String categoryName, Set<BlueprintRecipe> recipes, Consumer<Set<BlueprintRecipe>> onActivate) implements UIComponent {
        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return new Sized(
                Size.staticSize(80, 20),
                new Button.Builder()
                    .withOnPress((btn) -> onActivate.accept(recipes))
                    .build(new Center(new Text.Builder(categoryName))));
        }
    }
    
    private record Building(BlueprintRecipe recipe) implements UIComponent {
        @Override
        public UIComponent build(Layout layout, Theme theme) {
            return new Row.Builder()
                .build(
                    new Sized(
                        Size.staticSize(20, 20),
                        new Render(new ItemRenderProvider(recipe.output()))),
                    new Text.Builder(recipe.output().getName(new ItemStack(recipe.output())))
                );
        }
    }
}