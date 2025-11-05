package sbs.mira.mapdev;

import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sbs.mira.core.model.MiraConfigurationModel;
import sbs.mira.core.model.MiraPluginModel;

public
class MiraMapDevModel
  extends MiraPluginModel<MiraMapDevPulse, MiraMapDevPlayer>
{
  private @Nullable MiraConfigurationModel<MiraMapDevPulse> messages;
  
  public
  MiraMapDevModel( @NotNull MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  @Override
  public
  void breathe( )
  {
    super.breathe( );
    this.messages = new MiraConfigurationModel<>( this.pulse( ), "mapdev_messages.yml" );
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
  public @NotNull
  String find_message( @NotNull String key )
  {
    String result = messages.get( key );
    
    if ( result == null )
    {
      result = super.find_message( key );
    }
    
    return result;
  }
}
