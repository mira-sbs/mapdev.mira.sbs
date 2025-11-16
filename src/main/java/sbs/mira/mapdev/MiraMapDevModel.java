package sbs.mira.mapdev;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sbs.mira.core.model.MiraConfigurationModel;
import sbs.mira.core.model.MiraPluginDataModel;

public
class MiraMapDevModel
  extends MiraPluginDataModel<MiraMapDevPulse, MiraMapDevPlayer>
{
  private @Nullable MiraConfigurationModel<MiraMapDevPulse> messages;
  
  public
  MiraMapDevModel( @NotNull MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  @Override
  public
  void initialise( )
  {
    super.initialise( );
    this.messages = new MiraConfigurationModel<>( this.pulse( ), "mapdev_messages.yml" );
  }
  
  @Override
  @NotNull
  public
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
