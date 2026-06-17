// DamageAdditionalCommands.java
package net.anas.damageadditionalmod;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.network.chat.Component;

public class DamageAdditionalCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(Commands.literal("damageadditional")
                        .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                        .then(Commands.literal("get").executes(DamageAdditionalCommands::executeGet))
                        .then(Commands.literal("set")
                                .then(Commands.argument("value", DoubleArgumentType.doubleArg(0))
                                        .executes(DamageAdditionalCommands::executeSet)))));
    }

    private static int executeGet(CommandContext<CommandSourceStack> context) {
        double current = DamageAdditionalConfig.INSTANCE.killDamageBonus;
        context.getSource().sendSuccess(() -> Component.literal("Bonus damage per kill: +" + current), false);
        return 1;
    }

    private static int executeSet(CommandContext<CommandSourceStack> context) {
        double value = DoubleArgumentType.getDouble(context, "value");
        DamageAdditionalConfig.INSTANCE.killDamageBonus = value;
        DamageAdditionalConfig.save();
        context.getSource().sendSuccess(() -> Component.literal("Set bonus damage per kill to +" + value), true);
        return 1;
    }
}