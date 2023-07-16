package com.github.wintersteve25.oniutils.common.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;
import java.util.function.Predicate;

public final class PartialItemIngredient implements Predicate<ItemStack> {
    private final Ingredient ingredient;
    private int count;

    public PartialItemIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }
    
    public void shrink(int count) {
        this.count -= count;
    }

    public int getCount() {
        return count;
    }

    public static PartialItemIngredient deserialize(JsonElement input) {
        JsonObject object = input.getAsJsonObject();
        Ingredient ing = Ingredient.fromJson(object.get("item"));
        int count = object.get("count").getAsInt();
        return new PartialItemIngredient(ing, count);
    }

    public void encode(FriendlyByteBuf buf) {
        ingredient.toNetwork(buf);
        buf.writeInt(count);
    }

    public static PartialItemIngredient decode(FriendlyByteBuf buf) {
        return new PartialItemIngredient(Ingredient.fromNetwork(buf), buf.readInt());
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return ingredient.test(itemStack);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PartialItemIngredient) obj;
        return Objects.equals(this.ingredient, that.ingredient) &&
                this.count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, count);
    }

    @Override
    public String toString() {
        return "PartialItemIngredient[" +
                "ingredient=" + ingredient + ", " +
                "count=" + count + ']';
    }
}
