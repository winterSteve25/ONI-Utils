package com.github.wintersteve25.oniutils.common.network;

import com.github.wintersteve25.oniutils.common.contents.modules.items.gadgets.blueprint.ONIBlueprintItem;
import com.github.wintersteve25.oniutils.common.contents.modules.recipes.blueprints.BlueprintRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetBlueprintRecipe {
    
    private final ResourceLocation recipe;
    
    public PacketSetBlueprintRecipe(BlueprintRecipe recipe) {
        this.recipe = recipe.id();
    }
    
    public PacketSetBlueprintRecipe(FriendlyByteBuf buf) {
        this.recipe = buf.readResourceLocation();
    }
    
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(recipe);
    } 
    
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ONIBlueprintItem.setRecipe(ctx.get().getSender(), recipe));
        ctx.get().setPacketHandled(true);
    }
}
