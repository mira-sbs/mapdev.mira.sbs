package sbs.mira.mapdev;

import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sbs.mira.core.MiraPluginMaster;
import sbs.mira.core.module.MiraConfigurationModule;

public
class MiraMapDevMaster
  extends MiraPluginMaster<MiraMapDevPulse, MiraMapDevPlayer>
{
  private @Nullable MiraConfigurationModule<MiraMapDevPulse> mapdev_messages;
  
  public
  MiraMapDevMaster( @NotNull MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  @Override
  public
  void breathe( )
  {
    super.breathe( );
    this.mapdev_messages = new MiraConfigurationModule<>( this.pulse( ), "mapdev_messages.yml" );
  }
  
  @Override
  public @NotNull
  MiraMapDevPlayer declares( @NotNull CraftPlayer target )
  {
    return null;
  }
  
  @Override
  public
  void spectating( @NotNull MiraMapDevPlayer wp )
  {
  }
  
  @Override
  @NotNull
  public
  String find_message( @NotNull String key )
  {
    String result = mapdev_messages.get( key );
    
    if ( result == null )
    {
      result = super.find_message( key );
    }
    
    return result;
  }
}
