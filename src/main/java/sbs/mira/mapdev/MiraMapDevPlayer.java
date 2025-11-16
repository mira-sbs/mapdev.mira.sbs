package sbs.mira.mapdev;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sbs.mira.core.model.MiraPlayerModel;

public
class MiraMapDevPlayer
  extends MiraPlayerModel<MiraMapDevPulse>
{
  public
  MiraMapDevPlayer( @NotNull Player player, @NotNull MiraMapDevPulse pulse )
  {
    super( player, pulse );
  }
  
  @Override
  public
  void update( )
  {
  
  }
}
