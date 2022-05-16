package wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.common.data.capabilities.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetGermAmountCommands implements Command<CommandSourceStack> {

    private static final SetGermAmountCommands INSTANCE = new SetGermAmountCommands();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        List<String> germTypes = Arrays.stream(EnumGermTypes.values()).map((var) -> MiscHelper.langToReg(var.getName())).collect(Collectors.toList());

        return Commands.literal("setGermAmount")
                .requires(cs -> cs.hasPermission(1))
                .then(Commands.argument("target", EntityArgument.entities())
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .then(Commands.argument("germType", StringArgumentType.string()).suggests((ctx, sb) -> SharedSuggestionProvider.suggest(germTypes.stream(), sb))
                                        .executes(INSTANCE))));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Entity target = EntityArgument.getEntity(context, "target");
        String name = StringArgumentType.getString(context, "germType");

        if (target == null) {
            context.getSource().sendSuccess(new TranslatableComponent("oniutils.commands.set.failed.entityIsNull"), true);
            return 0;
        }

        if (name.isEmpty()) {
            context.getSource().sendSuccess(new TranslatableComponent("oniutils.commands.germs.set.failed.typeIsNull"), true);
        }

        target.getCapability(ONICapabilities.GERMS).ifPresent(t -> {
            t.setGerm(EnumGermTypes.getGermFromName(name), amount);
        });

        context.getSource().sendSuccess(new TranslatableComponent("oniutils.commands.germs.set.sucess", name.replace('_', ' '), amount), true);

        return 1;
    }
}
