package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class WorldChatCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  WorldChatCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"worldchat", "wc"}, usage = "<message>", desc = "Sends a message only to those in a world", min = 1
  )
  @CommandPermissions({"mapdev.loadworldfromrepo"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (sender instanceof ConsoleCommandSender)
    {
      sender.sendMessage(ChatColor.RED + "Console can't do this.");
      return;
    }
    Player p = (Player) sender;
    for (Player pl : p.getWorld().getPlayers())
    {
      pl.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "WorldChat" + ChatColor.DARK_AQUA + "] " + p.getName() + ": " + ChatColor.AQUA + args.getJoinedStrings(
        0));
    }
  }
}
