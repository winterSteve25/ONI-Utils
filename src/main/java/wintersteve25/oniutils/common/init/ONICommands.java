package wintersteve25.oniutils.common.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.commands.SetGermAmountCommands;

public class ONICommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> requires = Commands.literal("oniutils").requires((commandSource) -> commandSource.hasPermissionLevel(3));
        LiteralCommandNode<CommandSource> source = dispatcher.register(requires.then(SetGermAmountCommands.register(dispatcher)));
        dispatcher.register(Commands.literal("veiledascent").redirect(source));

        ONIUtils.LOGGER.info("Registered ONIUtils Commands!");
    }
}
