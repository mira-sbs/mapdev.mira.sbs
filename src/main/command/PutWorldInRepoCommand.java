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
class PutWorldInRepoCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  PutWorldInRepoCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(aliases = {"putworldinrepo", "putmapinrepo"},
           usage = "<WorldName>",
           desc = "Puts a world into the maps repository",
           min = 1,
           max = 1)
  @CommandPermissions({"mapdev.putworldinrepo"})
  public void putWorldInRepo(CommandContext args, CommandSender sender) throws CommandException
  {
    if (WorldUtil.putMapInRepo(args.getString(0).toLowerCase()))
      sender.sendMessage(ChatColor.DARK_AQUA + "Copied and put world " + ChatColor.AQUA + args.getString(0).toLowerCase() + ChatColor.DARK_AQUA + " into the maps repository!");
    else sender.sendMessage(ChatColor.RED + "Couldn't copy world into maps repo! Did you misspell the world?");
  }
}
