package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class UnloadWorldCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  
  public
  UnloadWorldCommand(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"unloadworld"}, usage = "<WorldName>", desc = "Unloads a world.", min = 1, max = 1
  )
  @CommandPermissions({"mapdev.unloadworld"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (WorldUtil.unloadWorld(args.getString(0).toLowerCase()))
    {
      sender.sendMessage(ChatColor.DARK_AQUA + "Unloaded world " + ChatColor.AQUA + args.getString(0).toLowerCase());
    }
    else
    {
      sender.sendMessage(ChatColor.RED + "You cannot unload this world. Is it unloaded already or can't you unload this?");
    }
  }
}
