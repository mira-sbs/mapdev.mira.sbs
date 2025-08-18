package sbs.mira.mapdev.guard;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import sbs.mira.core.module.MiraEventModule;
import sbs.mira.mapdev.MiraMapDevPulse;
import sbs.mira.mapdev.Util;

public
class MiraMapDevTeleportGuard
  extends MiraEventModule<PlayerTeleportEvent, MiraMapDevPulse>
{
  
  public
  MiraMapDevTeleportGuard(MiraMapDevPulse pulse)
  {
    super(pulse);
  }
  
  @Override
  @EventHandler
  public
  void occurs(PlayerTeleportEvent event)
  {
    World fromWorld = event.getFrom().getWorld();
    World toWorld = event.getTo().getWorld();
    Player p = event.getPlayer();
    if (toWorld != fromWorld)
    {
      if (Util.isPrivate(toWorld.getName()))
      {
        if (Util.isMember(toWorld.getName(), p.getName()) || p.hasPermission("mapdev.staff"))
        {
          if (p.hasPermission("mapdev.staff"))
          {
            p.sendMessage(ChatColor.RED + "[WARNING] This world is private, but you bypassed it with your permissions!");
          }
          p.sendMessage(ChatColor.GREEN + "This world is private, but you are allowed in!");
        }
        else
        {
          p.sendMessage(ChatColor.RED + "That world is private, you cannot go into it!");
          event.setCancelled(true);
        }
      }
    }
  }
}
