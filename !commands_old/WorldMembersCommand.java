package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;
import sbs.mira.mapdev.Util;

public
class WorldMembersCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  
  public
  WorldMembersCommand(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"members", "prvmembers", "membermanagement"},
    usage = "<add/remove/list>",
    desc = "Private world member management",
    min = 1
  )
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (args.getString(0).equalsIgnoreCase("add"))
    {
      if (args.argsLength() == 3)
      {
        if (pulse().plugin().getConfig().contains("worlds." + args.getString(1)))
        {
          if (!pulse()
            .plugin()
            .getConfig()
            .getString("worlds." + args.getString(1) + ".owner")
            .equals(sender.getName()))
          {
            sender.sendMessage(ChatColor.RED + "You are not the owner of that world.");
            if (!pulse().plugin().has_permission(sender, "mapdev.staff"))
            {
              return;
            }
            sender.sendMessage(ChatColor.RED + "But you had overriding permissions!");
            if (!Util.isMember(args.getString(1), args.getString(2)))
            {
              Util.addMember(args.getString(1), args.getString(2));
              sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " can now access " + ChatColor.RED + args.getString(
                1));
              return;
            }
            else
            {
              sender.sendMessage(ChatColor.RED + args.getString(2) + " is already a member of " + args.getString(1) + "!");
              return;
            }
          }
          else
          {
            if (!Util.isMember(args.getString(1), args.getString(2)))
            {
              Util.addMember(args.getString(1), args.getString(2));
              sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " can now access " + ChatColor.RED + args.getString(
                1));
            }
            else
            {
              sender.sendMessage(ChatColor.RED + args.getString(2) + " is already a member of " + args.getString(1) + "!");
              return;
            }
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "The world " + args.getString(1) + " hasn't been privated, it can't inherit members!");
          return;
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + "Usage: /members add <world> <player>");
        return;
      }
    }
    if (args.getString(0).equalsIgnoreCase("remove"))
    {
      if (args.argsLength() == 3)
      {
        if (pulse().plugin().getConfig().contains("worlds." + args.getString(1)))
        {
          if (!pulse()
            .plugin()
            .getConfig()
            .getString("worlds." + args.getString(1) + ".owner")
            .equals(sender.getName()))
          {
            sender.sendMessage(ChatColor.RED + "You are not the owner of that world.");
            if (!pulse().plugin().has_permission(sender, "mapdev.staff"))
            {
              return;
            }
            sender.sendMessage(ChatColor.RED + "But you had overriding permissions!");
            if (Util.isMember(args.getString(1), args.getString(2)))
            {
              Util.removeMember(args.getString(1), args.getString(2));
              sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " can no longer access " + ChatColor.RED + args.getString(
                1));
            }
            else
            {
              sender.sendMessage(ChatColor.RED + args.getString(2) + " isn't a member of " + args.getString(1) + "!");
            }
          }
          else
          {
            if (Util.isMember(args.getString(1), args.getString(2)))
            {
              Util.removeMember(args.getString(1), args.getString(2));
              sender.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED + args.getString(2) + ChatColor.GREEN + " can no longer access " + ChatColor.RED + args.getString(
                1));
            }
            else
            {
              sender.sendMessage(ChatColor.RED + args.getString(2) + " isn't a member of " + args.getString(1) + "!");
            }
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "The world " + args.getString(1) + " hasn't been privated, it can't de-inherit members!");
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + "Usage: /members remove <world> <player>");
      }
    }
    if (args.getString(0).equalsIgnoreCase("list"))
    {
      if (sender instanceof Player player)
      {
        if (!pulse().plugin().has_permission(sender, "mapdev.staff"))
        {
          return;
        }
        if (pulse().plugin().getConfig().contains("worlds." + player.getWorld().getName()))
        {
          player.sendMessage(ChatColor.GREEN + "The members allowed in this world are:");
          player.sendMessage(Util.getMembers(player.getWorld().getName()).toString());
        }
      }
      else
      {
        if (args.argsLength() > 1)
        {
          if (pulse().plugin().getConfig().contains("worlds." + args.getString(1)))
          {
            sender.sendMessage(ChatColor.GREEN + "The members allowed in this world are:");
            sender.sendMessage(Util.getMembers(args.getString(1)).toString());
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "Usage: /members list <world>");
        }
      }
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "Usage: /members <add/remove/list>");
    }
  }
}
