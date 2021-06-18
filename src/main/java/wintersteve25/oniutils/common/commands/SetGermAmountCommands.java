package wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;

public class SetGermAmountCommands implements Command<CommandSource> {

    private static final SetGermAmountCommands INSTANCE = new SetGermAmountCommands();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {

        return Commands.literal("setGermAmount")
                .requires(cs -> cs.hasPermission(1))
                .then(Commands.argument("target", EntityArgument.entities()))
                .then(Commands.argument("amount", IntegerArgumentType.integer()))
                .executes(INSTANCE);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Entity target = EntityArgument.getEntity(context, "target");

        target.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(t -> {
            t.setGerm(t.getGermType(), amount);
        });
        return 1;
    }
}
