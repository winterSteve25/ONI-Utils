package wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.capability.germ.GermCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetGermAmountCommands implements Command<CommandSource> {

    private static final SetGermAmountCommands INSTANCE = new SetGermAmountCommands();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {

        List germTypes = Arrays.stream(EnumGermTypes.values()).map(EnumGermTypes::getName).collect(Collectors.toList());

        return Commands.literal("setGermAmount")
                .requires(cs -> cs.hasPermissionLevel(1))
                .then(Commands.argument("target", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .then(Commands.argument("germType", StringArgumentType.string()).suggests((ctx, sb) -> ISuggestionProvider.suggest(germTypes.stream(), sb))
                                        .executes(INSTANCE))));
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Entity target = EntityArgument.getEntity(context, "target");
        String name = StringArgumentType.getString(context, "germType");

        if (target == null) {
            context.getSource().sendFeedback(new TranslationTextComponent("oniutils.commands.germs.set.failed.entityIsNull"), true);
            return 0;
        }

        if (name.isEmpty()) {
            context.getSource().sendFeedback(new TranslationTextComponent("oniutils.commands.germs.set.failed.typeIsNull"), true);
        }

        target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
            t.setGerm(EnumGermTypes.getGermFromName(name), amount);
        });

        context.getSource().sendFeedback(new TranslationTextComponent("oniutils.commands.germs.set.sucess", name.replace('_', ' '), amount), true);

        return 1;
    }
}
