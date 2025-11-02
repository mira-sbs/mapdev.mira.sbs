package sbs.mira.mapdev.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class TerraformCommand
  extends MiraCommandModule<MiraMapDevPulse>
{
  public
  TerraformCommand(@NotNull MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Command(
    aliases = {"terraform", "tf"},
    usage = "/terraform",
    desc = "Adds Terraforming tools to your inventory.",
    flags = "l"
  )
  @CommandPermissions({"mapdev.terraform"})
  public
  void execute(@NotNull CommandContext args, @NotNull CommandSender sender)
  {
    if (sender instanceof Player p)
    {
      p.getInventory().clear();
      p.getInventory().setItem(0, new ItemStack(Material.COMPASS));
      p.getInventory().setItem(1, new ItemStack(Material.WOODEN_AXE));
      p.getInventory().setItem(2, new ItemStack(Material.ARROW));
      p.getInventory().setItem(3, new ItemStack(Material.DIRT));
      p.getInventory().setItem(4, new ItemStack(Material.STONE));
      p.getInventory().setItem(5, new ItemStack(Material.DIAMOND_PICKAXE));
      
      if (args.hasFlag('l'))
      {
        p.getInventory().setItem(6, new ItemStack(Material.TALL_GRASS));
        p.getInventory().setItem(7, new ItemStack(Material.SAND));
        p.getInventory().setItem(8, new ItemStack(Material.LEATHER_HELMET));
        p.sendMessage(ChatColor.DARK_AQUA + "Inventory replaced with Leet TerraForming tools!");
      }
      else
      {
        p.sendMessage(ChatColor.DARK_AQUA + "Inventory replaced with TerraForming tools!");
      }
    }
    else
    {
      sender.sendMessage("You must be a player to use this command!");
    }
  }
}
