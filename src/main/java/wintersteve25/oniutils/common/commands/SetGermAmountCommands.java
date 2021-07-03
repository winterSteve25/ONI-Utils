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
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.oniutils.common.capability.germ.GermCapability;

public class SetGermAmountCommands implements Command<CommandSource> {

    private static final SetGermAmountCommands INSTANCE = new SetGermAmountCommands();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {

        return Commands.literal("setGermAmount")
                .requires(cs -> cs.hasPermissionLevel(1))
                .then(Commands.argument("target", EntityArgument.entities()))
                .then(Commands.argument("amount", IntegerArgumentType.integer()))
                .executes(INSTANCE);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Entity target = EntityArgument.getEntity(context, "target");

        if (amount == 0) {
            context.getSource().sendFeedback(new TranslationTextComponent("oniutils.commands.germs.set.failed.amountIsZero"), true);
            return 0;
        }

        if (target == null) {
            context.getSource().sendFeedback(new TranslationTextComponent("oniutils.commands.germs.set.failed.entityIsNull"), true);
            return 0;
        }

        target.getCapability(GermCapability.GERM_CAPABILITY).ifPresent(t -> {
            t.setGerm(t.getGermType(), amount);
        });
        return 1;
    }
}
