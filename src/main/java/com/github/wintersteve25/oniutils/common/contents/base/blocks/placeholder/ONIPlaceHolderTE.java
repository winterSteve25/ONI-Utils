package com.github.wintersteve25.oniutils.common.contents.base.blocks.placeholder;

import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseInvTE;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipe;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import com.github.wintersteve25.oniutils.common.registries.ONIRecipes;
import com.github.wintersteve25.oniutils.common.utils.PartialItemIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ONIPlaceHolderTE extends ONIBaseInvTE {
    
    private BlueprintRecipe recipe;
    private List<PartialItemIngredient> remainingIngredients;
    private float completionPercentage;
    
    public ONIPlaceHolderTE(BlockPos pos, BlockState state) {
        this(ONIBlocks.Misc.PLACEHOLDER_TE.get(), pos, state);
    }

    public ONIPlaceHolderTE(BlockEntityType<ONIPlaceHolderTE> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        init(new BlueprintRecipe(new ResourceLocation(ONIUtils.MODID, "test"), NonNullList.create(), ONIBlocks.Machines.Power.COAL_GEN_BLOCK.asItem()));
    }

    @Override
    public int getInvSize() {
        return 32;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        completionPercentage = tag.getFloat("completion");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("completion", completionPercentage);
    }

    public void init(BlueprintRecipe recipe) {
        if (hasItem()) {
            throw new IllegalStateException("Should not initialize Placeholder with recipe after it is initialized and has items");
        }
        
        this.recipe = recipe;
        this.remainingIngredients = new ArrayList<>(recipe.ingredients());
        completionPercentage = 0f / remainingIngredients.size();
    }
    
    public boolean addItem(ItemStack itemStack) {
        if (recipe == null) return false;
        Optional<PartialItemIngredient> ingredient = remainingIngredients.stream()
                .filter(ing -> ing.test(itemStack))
                .findFirst();
        
        if (ingredient.isEmpty()) return false;
        for (int i = 0; i < getInvSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                 continue;
            }
            
            itemHandler.insertItem(i, itemStack.copy(), false);
            
            int shrinkCount = Math.min(itemStack.getCount(), ingredient.get().getCount());
            itemStack.shrink(shrinkCount);
            
            ingredient.get().shrink(shrinkCount);
            if (ingredient.get().getCount() <= 0) {
                remainingIngredients.remove(ingredient.get());
            }
            
            completionPercentage = 0f / remainingIngredients.size();
            updateBlock();
            return true;
        }
        
        return false;
    }

    public BlockItem getInPlaceOf() {
        return recipe.output();
    }

    public float getCompletionPercentage() {
        return completionPercentage;
    }
}