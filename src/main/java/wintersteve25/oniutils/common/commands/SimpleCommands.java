package wintersteve25.oniutils.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.network.chat.TranslatableComponent;
import wintersteve25.oniutils.common.registries.ONICapabilities;
import wintersteve25.oniutils.common.utils.helpers.LangHelper;

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
                        .executes((cs) -> {
                            var player = cs.getSource().getPlayerOrException();
                            player.teleportTo(DimensionArgument.getDimension(cs, "dimension"), player.getX(), player.getY(), player.getZ(), 0, 0);
                            return 1;
                        }));
    }
}
