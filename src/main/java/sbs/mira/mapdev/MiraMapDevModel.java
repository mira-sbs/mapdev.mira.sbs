package sbs.mira.mapdev;

import org.bukkit.craftbukkit.v1_21_R6.entity.CraftPlayer;
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
  public @NotNull
  MiraMapDevPlayer declares( @NotNull CraftPlayer target )
  {
    return null;
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
