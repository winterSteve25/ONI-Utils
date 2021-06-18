package wintersteve25.oniutils.common.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.commands.SetGermAmountCommands;

/**
 *Commands are not yet registered in this mod
 */

public class ONICommands {
    private static final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public static void register() {
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(
                Commands.literal(ONIUtils.MODID)
                        .then(SetGermAmountCommands.register(dispatcher))
        );

        dispatcher.register(Commands.literal(ONIUtils.MODID).redirect(cmd));
    }
}
