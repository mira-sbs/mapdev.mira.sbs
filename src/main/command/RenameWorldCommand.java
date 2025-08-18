package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public
class RenameWorldCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  RenameWorldCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(aliases = {"renameworld"},
           usage = "<OriginalWorldName> <NewName>",
           desc = "Copies, renames & loads a world",
           flags = "d",
           min = 2, max = 2)
  @CommandPermissions({"mapdev.renameworld"})
  public void renameWorld(CommandContext args, CommandSender sender) throws CommandException
  {
    try {
      Bukkit.getWorld(args.getString(0)).save();
      WorldUtil.copyFolder(new File(args.getString(0)), new File(args.getString(1)));
      File tar = new File(args.getString(1));
      for (File f : tar.listFiles()) {
        if (f.getName().equals("uid.dat")) {
          f.delete();
        }
      }
      WorldCreator worldc = new WorldCreator(args.getString(1));
      worldc.generator(new com.oresomecraft.mapdev.generators.NullChunkGenerator());
      Bukkit.createWorld(worldc);
    } catch (IOException e) {
      if (e instanceof FileNotFoundException) {
        sender.sendMessage(ChatColor.RED + "Something went wrong. Perhaps that world doesn't exist?");
        return;
      }
      e.printStackTrace();
      //Love, why didn't the world copy?
    }
    if (args.hasFlag('d')) {
      Bukkit.dispatchCommand(sender, "worldtp " + args.getString(1));
      WorldUtil.discardWorld(args.getString(0));
      sender.sendMessage(ChatColor.RED + "WARNING: You used the -d flag and deleted the original map!");
    }
    sender.sendMessage(ChatColor.DARK_AQUA + "Copied world '" + ChatColor.AQUA +
                         args.getString(0) + ChatColor.DARK_AQUA + "' and renamed it to '" + ChatColor.AQUA + args.getString(1) + ChatColor.DARK_AQUA + "'!");
  }

}
