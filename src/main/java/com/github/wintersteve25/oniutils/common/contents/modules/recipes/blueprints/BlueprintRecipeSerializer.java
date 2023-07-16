package com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints;

import com.github.wintersteve25.oniutils.common.utils.PartialItemIngredient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class BlueprintRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BlueprintRecipe> {

    /**
     * EXAMPLE JSON
     * {
     *     "inputs": [
     *          {
     *              "item": "minecraft:iron_ingot",
     *              "count": 5
     *          }
     *     ],
     *     "output": "oniutils:coal_generator"
     * }
     */
    
    @Override
    public BlueprintRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
        JsonArray inputs = json.getAsJsonArray("inputs");
        
        if (inputs.size() > 32) {
            throw new IllegalStateException("Blueprint recipes can not have more than 32 ingredients");
        }
        
        NonNullList<PartialItemIngredient> ingredients = NonNullList.create();
        
        for (JsonElement input : inputs) {
            ingredients.add(PartialItemIngredient.deserialize(input));
        }

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get("output").getAsString()));
        
        if (item instanceof BlockItem blockItem) {
            return new BlueprintRecipe(pRecipeId, ingredients, blockItem);
        }
        
        throw new IllegalArgumentException("Blueprint output must be a blockitem");
    }

    @Nullable
    @Override
    public BlueprintRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        NonNullList<PartialItemIngredient> ings = pBuffer.readCollection(c -> NonNullList.create(), PartialItemIngredient::decode);
        BlockItem output = (BlockItem) pBuffer.readItem().getItem();
        return new BlueprintRecipe(pRecipeId, ings, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, BlueprintRecipe pRecipe) {
        pBuffer.writeCollection(pRecipe.ingredients(), (buf, ing) -> ing.encode(buf));
        pBuffer.writeItem(new ItemStack(pRecipe.output()));
    }
}
