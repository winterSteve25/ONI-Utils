package com.github.wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mekanism.common.network.to_client.PacketPlayerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import com.github.wintersteve25.oniutils.common.data.capabilities.germ.api.EnumGermType;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.PlayerData;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.SkillType;
import com.github.wintersteve25.oniutils.common.registries.ONICapabilities;
import com.github.wintersteve25.oniutils.common.utils.helpers.LangHelper;
import com.github.wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SimpleCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> getGermCommand() {
        return Commands.literal("get").executes((cs) -> {
            var player = cs.getSource().getPlayerOrException();
            player.getCapability(ONICapabilities.GERMS).ifPresent(cap -> {
                cs.getSource().sendSuccess(new TranslatableComponent("oniutils.commands.germs.get.success", Integer.toString(cap.getGermAmount()), LangHelper.germ(cap.getGermType().getName())), true);
            });
            return 1;
        });
    }

    public static LiteralArgumentBuilder<CommandSourceStack> teleportDimensionCommand() {
        return Commands.literal("telDim")
                .then(Commands.argument("dimension", DimensionArgument.dimension())
                        .executes(cs -> {
                            var player = cs.getSource().getPlayerOrException();
                            player.teleportTo(DimensionArgument.getDimension(cs, "dimension"), player.getX(), player.getY(), player.getZ(), 0, 0);
                            return 1;
                        }));
    }
    
    public static LiteralArgumentBuilder<CommandSourceStack> setSkillLevelCommand() {
        Stream<String> skillTypes = Arrays.stream(SkillType.values()).map((var) -> var.name());
        return Commands.literal("set")
            .then(
                Commands.argument("skillType", StringArgumentType.string())
                    .suggests((ctx, sb) -> SharedSuggestionProvider.suggest(skillTypes, sb))
                    .then(
                        Commands.argument("level", IntegerArgumentType.integer())
                            .executes(cs -> {
                                var player = cs.getSource().getPlayerOrException();
                                player.getCapability(ONICapabilities.PLAYER).ifPresent(playerData -> {
                                    playerData.setLevel(
                                        SkillType.valueOf(StringArgumentType.getString(cs, "skillType")), 
                                        IntegerArgumentType.getInteger(cs, "level")    
                                    );
                                });
                                return 1;
                            }))
            );
    }
    
    public static LiteralArgumentBuilder<CommandSourceStack> getSkillLevelCommand() {
        return Commands.literal("get")
                .executes(cs -> {
                    var player = cs.getSource().getPlayerOrException();
                    player.getCapability(ONICapabilities.PLAYER).ifPresent(playerData -> {
                        cs.getSource().sendSuccess(new TextComponent(playerData.getSkills().toString()), true);
                    });
                    return 1;
                });
    }
}
