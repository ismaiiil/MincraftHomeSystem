package com.ismail.homesystem.spigot.commands;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@Command("home")
public class HomeCommand {

    @Default
    public static void home(CommandSender sender) {
        sender.sendPlainMessage("Opening the GUI, you may use \"/home help\" for details on other commands");
    }

}
