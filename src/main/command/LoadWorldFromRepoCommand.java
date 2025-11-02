package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class LoadWorldFromRepoCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  
  public
  LoadWorldFromRepoCommand(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"loadworldfromrepo", "loadmapfromrepo"},
    usage = "<WorldName>",
    desc = "Loads a world from the maps repo",
    min = 1,
    max = 1
  )
  @CommandPermissions({"mapdev.loadworldfromrepo"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (WorldUtil.loadWorldFromRepo(args.getString(0).toLowerCase()))
    {
      sender.sendMessage(ChatColor.DARK_AQUA + "Copied and loaded world " + ChatColor.AQUA + args
        .getString(0)
        .toLowerCase() + ChatColor.DARK_AQUA + " from maps repository!");
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "Unable to load world from maps repo!");
      sender.sendMessage(ChatColor.RED + "Please double check the name or your spelling...");
    }
  }
}
