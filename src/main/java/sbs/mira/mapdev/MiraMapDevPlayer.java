package sbs.mira.mapdev;

import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import sbs.mira.core.MiraPlayer;

public
class MiraMapDevPlayer
  extends MiraPlayer<MiraMapDevPulse>
{
  public
  MiraMapDevPlayer(
    @NotNull CraftPlayer player,
    @NotNull MiraMapDevPulse pulse
  )
  {
    super(player, pulse);
  }
}
