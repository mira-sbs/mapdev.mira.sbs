package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class DiscardWorldCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  DiscardWorldCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(aliases = {"discardworld"},
           usage = "<WorldName>",
           desc = "Unloads and deletes a world",
           min = 1,
           max = 1)
  @CommandPermissions({"mapdev.discardworld"})
  public void discardWorld(CommandContext args, CommandSender sender) throws CommandException
  {
    if (WorldUtil.discardWorld(args.getString(0).toLowerCase())) {
      sender.sendMessage(ChatColor.DARK_AQUA + "Deleted and unloaded " + ChatColor.AQUA + args.getString(0).toLowerCase());
    } else {
      sender.sendMessage(ChatColor.RED + "Couldn't discard world, did you get the name right?");
    }
  }
}
