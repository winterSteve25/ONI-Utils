package wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

public class SimpleCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> registerReloadCommand (CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("get").executes((cs) -> {
            var player = cs.getSource().getPlayerOrException();
            player.getCapability(ONICapabilities.GERMS).ifPresent(cap -> {
                cs.getSource().sendSuccess(new TranslatableComponent("oniutils.commands.germs.get.success", Integer.toString(cap.getGermAmount()), LangHelper.germ(cap.getGermType().getName())), true);
            });
            return 1;
        });
    }
}
