package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class WorldSetSpawnCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  WorldSetSpawnCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  
  @Command(aliases = {"worldsetspawn"},
           desc = "Sets spawn for a world.")
  @CommandPermissions({"mapdev.worldsetspawn"})
  public void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      World world = p.getWorld();
      world.setSpawnLocation((int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
      sender.sendMessage(ChatColor.AQUA + "Set spawn point for world '" + p.getWorld().getName() + "'");
    } else {
      sender.sendMessage("You must be a player to use this command!");
    }
  }
}
