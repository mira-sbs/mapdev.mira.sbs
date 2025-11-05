package sbs.mira.mapdev.command;

import app.ashcon.intake.Command;
import app.ashcon.intake.parametric.annotation.Switch;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sbs.mira.core.model.MiraCommandModel;
import sbs.mira.mapdev.MiraMapDevPulse;

public
class TerraformCommand
  extends MiraCommandModel<MiraMapDevPulse>
{
  public
  TerraformCommand( @NotNull MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  @Command (
    aliases = { "terraform", "tf" },
    usage = "/terraform",
    desc = "adds common terraforming items to your (now cleared) inventory.",
    perms = "mira.terraform",
    flags = "l"
  )
  public
  void terraform( @NotNull CommandSender sender, @Switch ('l') boolean wants_elite )
  {
    if ( !( sender instanceof Player player ) )
    {
      sender.sendMessage( pulse( ).model( ).message( "error.non_player" ) );
      return;
    }
    
    player.getInventory( ).clear( );
    player.getInventory( ).setItem( 0, new ItemStack( Material.COMPASS ) );
    player.getInventory( ).setItem( 1, new ItemStack( Material.WOODEN_AXE ) );
    player.getInventory( ).setItem( 2, new ItemStack( Material.ARROW ) );
    player.getInventory( ).setItem( 3, new ItemStack( Material.DIRT ) );
    player.getInventory( ).setItem( 4, new ItemStack( Material.STONE ) );
    player.getInventory( ).setItem( 5, new ItemStack( Material.DIAMOND_PICKAXE ) );
    
    if ( wants_elite )
    {
      player.getInventory( ).setItem( 6, new ItemStack( Material.TALL_GRASS ) );
      player.getInventory( ).setItem( 7, new ItemStack( Material.SAND ) );
      player.getInventory( ).setItem( 8, new ItemStack( Material.LEATHER_HELMET ) );
      
      player.sendMessage( pulse( ).model( ).message( "info.player.terraform_tools", "elite " ) );
    }
    else
    {
      player.sendMessage( pulse( ).model( ).message( "info.player.terraform_tools", "" ) );
    }
  }
}
