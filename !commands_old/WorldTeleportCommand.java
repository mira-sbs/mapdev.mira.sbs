package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class WorldTeleportCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  WorldTeleportCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  
  @Command(
    aliases = {"worldtp"}, usage = "<WorldName>", desc = "Teleports you to a world."
  )
  @CommandPermissions({"mapdev.worldtp"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (sender instanceof Player p)
    {
      if (args.argsLength() < 1)
      {
        sender.sendMessage(ChatColor.RED + "Correct usage: /worldtp <WorldName>");
      }
      else
      {
        if (Bukkit.getWorld(args.getString(0)) != null)
        {
          p.teleport(Bukkit.getWorld(args.getString(0)).getSpawnLocation());
        }
        else
        {
          p.sendMessage(ChatColor.RED + "The map you specified doesn't exist or isn't loaded!");
        }
      }
    }
    else
    {
      sender.sendMessage("You must be a player to use this command!");
    }
  }
}
