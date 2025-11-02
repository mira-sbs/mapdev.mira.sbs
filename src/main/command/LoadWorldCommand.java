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
class LoadWorldCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  
  public
  LoadWorldCommand(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"loadworld", "createworld"},
    usage = "<WorldName>",
    desc = "Loads or creates a world.",
    min = 1,
    max = 1,
    flags = "tn"
  )
  @CommandPermissions({"mapdev.loadworld"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    WorldUtil.loadOrCreateWorld(args.getString(0).toLowerCase(), !args.hasFlag('n'));
    
    if (args.hasFlag('t'))
    {
      if (sender instanceof Player)
      {
        ((Player) sender).teleport(Bukkit.getWorld(args.getString(0)).getSpawnLocation());
      }
    }
    sender.sendMessage(ChatColor.DARK_AQUA + "Created/loaded world " + ChatColor.AQUA + args
      .getString(0)
      .toLowerCase());
  }
}
