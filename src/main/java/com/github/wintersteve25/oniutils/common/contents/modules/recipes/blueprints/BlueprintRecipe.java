package com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints;

import com.github.wintersteve25.oniutils.common.registries.ONIRecipes;
import com.github.wintersteve25.oniutils.common.utils.PartialItemIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.NoSuchElementException;
import java.util.Optional;

public record BlueprintRecipe(ResourceLocation id, NonNullList<PartialItemIngredient> ingredients, BlockItem output) implements Recipe<Container> {
    
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return true;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ONIRecipes.BLUEPRINT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ONIRecipes.BLUEPRINT_RECIPE_TYPE.get();
    }
    
    public static Optional<BlueprintRecipe> getRecipeWithId(Level level, ResourceLocation id) {
        return level.getRecipeManager()
                .getAllRecipesFor(ONIRecipes.BLUEPRINT_RECIPE_TYPE.get())
                .stream()
                .filter(r -> r.id().equals(id))
                .findFirst();
    }
}
