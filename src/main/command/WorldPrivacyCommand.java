package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;
import sbs.mira.mapdev.Util;

public final
class WorldPrivacyCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  
  public
  WorldPrivacyCommand(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"privacy", "worldprivacy", "mdprivate", "private"},
    usage = "<set/unset>",
    desc = "Manage region privacy",
    min = 1
  )
  @CommandPermissions({"mapdev.staff"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (args.getString(0).equalsIgnoreCase("set"))
    {
      if (args.argsLength() == 3)
      {
        if (Bukkit.getWorld(args.getString(1)) != null)
        {
          if (!pulse().plugin().getConfig().contains("worlds." + args.getString(1)))
          {
            Util.setPrivateWorld(args.getString(1), args.getString(2));
            sender.sendMessage(ChatColor.GREEN + "World " + ChatColor.RED + args.getString(1) + ChatColor.GREEN + " is now a private world!");
            sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " owns the world!");
          }
          else
          {
            sender.sendMessage(ChatColor.RED + "That world has already been claimed by " + pulse()
              .plugin()
              .getConfig()
              .getString("worlds." + args.getString(1) + ".owner"));
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "The world " + args.getString(1) + " could not be privated, perhaps it's not loaded?");
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + "Usage: /privacy set <world> <owner>");
      }
    }
    else if (args.getString(0).equalsIgnoreCase("unset"))
    {
      if (args.argsLength() == 2)
      {
        if (!args.getString(1).equalsIgnoreCase("plugins") && !args.getString(1).equalsIgnoreCase("world"))
        {
          if (pulse().plugin().getConfig().contains("worlds." + args.getString(1)))
          {
            Util.unsetPrivateWorld(args.getString(1));
            sender.sendMessage(ChatColor.GREEN + "World " + ChatColor.RED + args.getString(1) + ChatColor.GREEN + " is no longer a private world!");
          }
          else
          {
            sender.sendMessage(ChatColor.RED + "The world " + args.getString(1) + " doesn't exist or isn't privated!");
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "You can't private that!");
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + "Usage: /privacy unset <world>");
      }
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "Usage: /privacy <set/unset>");
    }
  }
}
