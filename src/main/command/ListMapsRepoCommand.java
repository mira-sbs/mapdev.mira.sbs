package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public
class ListMapsRepoCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  ListMapsRepoCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"listmapsrepo"}, desc = "Lists all maps in the defined repo"
  )
  @CommandPermissions({"mapdev.listmaps"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    int page = 1;
    if (args.argsLength() == 1)
    {
      try
      {
        page = Integer.parseInt(args.getString(0));
      }
      catch (NumberFormatException e)
      {
        sender.sendMessage(ChatColor.RED + "That is not a number!");
        return;
      }
    }
    ArrayList<String> worlds = new ArrayList<String>();
    for (File f : new File(WorldUtil.MAPS_REPO).listFiles())
    {
      if (f.isDirectory() && !(Arrays.asList(WorldUtil.disallowedFiles).contains(f.getName())))
      {
        worlds.add(f.getName());
      }
    }
    int maxPage = page * 10;
    int i = maxPage - 10;
    sender.sendMessage(ChatColor.GOLD + "Maps Repo List (Page " + page + ")");
    //10 per page, so if it's page 2 it will check the array-list from 10-20.
    boolean stopCheck = false;
    while (i < maxPage && !stopCheck)
    {
      try
      {
        sender.sendMessage(ChatColor.DARK_AQUA + "- " + ChatColor.AQUA + worlds.get(i));
        i++;
      }
      catch (IndexOutOfBoundsException e)
      {
        sender.sendMessage(ChatColor.RED + "No further maps found.");
        i++;
        stopCheck = true;
      }
    }
    sender.sendMessage(ChatColor.GOLD + "To see next page, type '/listmaps " + (page + 1) + "'");
  }
}
