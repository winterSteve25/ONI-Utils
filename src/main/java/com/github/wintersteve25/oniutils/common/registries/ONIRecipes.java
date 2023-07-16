package com.github.wintersteve25.oniutils.common.registries;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipeSerializer;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ONIRecipes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ONIUtils.MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ONIUtils.MODID);
    
    public static final RegistryObject<BlueprintRecipeType> BLUEPRINT_RECIPE_TYPE = RECIPE_TYPE_DEFERRED_REGISTER.register("blueprint", BlueprintRecipeType::new);
    public static final RegistryObject<BlueprintRecipeSerializer> BLUEPRINT_RECIPE_SERIALIZER = RECIPE_SERIALIZER_DEFERRED_REGISTER.register("blueprint", BlueprintRecipeSerializer::new);
    
    public static void register(IEventBus eventBus) {
        RECIPE_TYPE_DEFERRED_REGISTER.register(eventBus);
        RECIPE_SERIALIZER_DEFERRED_REGISTER.register(eventBus);
    }
}