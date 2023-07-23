package com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint;

import com.github.wintersteve25.oniutils.client.gui.ONIUITheme;
import com.github.wintersteve25.oniutils.client.utils.IngredientTooltip;
import com.github.wintersteve25.oniutils.client.utils.ItemTooltip;
import com.github.wintersteve25.oniutils.common.contents.base.ONIItemCategory;
import com.github.wintersteve25.oniutils.common.contents.base.items.ONIIItem;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipe;
import com.github.wintersteve25.oniutils.common.registries.ONIRecipes;
import com.github.wintersteve25.oniutils.common.utils.helpers.UISizeHelper;
import com.github.wintersteve25.tau.components.*;
import com.github.wintersteve25.tau.components.base.DynamicUIComponent;
import com.github.wintersteve25.tau.components.base.UIComponent;
import com.github.wintersteve25.tau.layout.Layout;
import com.github.wintersteve25.tau.layout.LayoutSetting;
import com.github.wintersteve25.tau.renderer.ScreenUIRenderer;
import com.github.wintersteve25.tau.theme.Theme;
import com.github.wintersteve25.tau.utils.Color;
import com.github.wintersteve25.tau.utils.ItemRenderProvider;
import com.github.wintersteve25.tau.utils.Pad;
import com.github.wintersteve25.tau.utils.Size;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.Arrays;
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
                new Category("Base", RECIPES.get()
                        .stream()
                        .filter(recipe -> recipe.output() instanceof ONIIItem i && i.getONIItemCategory() == ONIItemCategory.GENERAL)
                        .collect(Collectors.toSet()), recipes -> {
                    recipesShown = recipes;
                    rebuild();
                }),
                new Category("Power", RECIPES.get()
                        .stream()
                        .filter(recipe -> recipe.output() instanceof ONIIItem i && i.getONIItemCategory() == ONIItemCategory.POWER)
                        .collect(Collectors.toSet()), recipes -> {
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
                                    Size.staticSize(150, 200),
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
                            Size.staticSize(192, 200),
                            new Container.Builder()
                                .withChild(new ListView.Builder()
                                    .withSpacing(2)
                                    .withAlignment(LayoutSetting.START)
                                    .build(recipesShown.stream().map(Building::new).collect(Collectors.toUnmodifiableList()))
                                )),
                        categoryButtons()
                    )));
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ScreenUIRenderer(new ONIBlueprintGui(), true, ONIUITheme.INSTANCE));
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
            return new Padding(
                new Pad.Builder().top(4).left(4).right(4).build(),
                new Sized(
                    Size.percentage(1, 0.1f),
                    new Tooltip.Builder()
                        .withText(new TextComponent("Requires:"))
                        .withComponent(recipe.ingredients()
                            .stream()
                            .map(ingredient -> (ClientTooltipComponent) new IngredientTooltip(ingredient))
                            .toList())
                        .build(new Button.Builder()
                            .build(new Row.Builder()
                                .build(
                                    new Sized(
                                        UISizeHelper.squareWithY(1),
                                        new Render(new ItemRenderProvider(recipe.output()))),
                                    new Text.Builder(recipe.output().getName(new ItemStack(recipe.output())))
                                        .withColor(Color.WHITE))))
                )
            );
        }
    }
}