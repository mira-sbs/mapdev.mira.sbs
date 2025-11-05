package sbs.mira.mapdev;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import org.bukkit.plugin.PluginDescriptionFile;
import sbs.mira.core.MiraPlugin;
import sbs.mira.mapdev.command.MapCommand;
import sbs.mira.mapdev.command.MapsCommand;
import sbs.mira.mapdev.command.TerraformCommand;

public
class MiraMapDevPlugin
  extends MiraPlugin<MiraMapDevPulse>
{
  public
  MiraMapDevPlugin( )
  {
    super( new MiraMapDevPulse( ) );
  }
  
  @Override
  public
  void onLoad( )
  {
    super.onLoad( );
    
    this.pulse( ).breathe( this, new MiraMapDevModel( pulse( ) ) );
    
    BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph( );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapCommand( pulse( ) ) );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapsCommand( pulse( ) ) );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new TerraformCommand( pulse( ) ) );
    
    BukkitIntake intake = new BukkitIntake( this, cmdGraph );
    intake.register( );
    
    PluginDescriptionFile description = this.getDescription( );
    this.log( description.getName( ) + " v" + description.getVersion( ) + " is now loaded." );
  }
  
  @Override
  public
  void onEnable( )
  {
    super.onEnable( );
    
    PluginDescriptionFile description = this.getDescription( );
    this.log( description.getName( ) + " v" + description.getVersion( ) + " is now enabled." );
  }
  
  @Override
  public
  void onDisable( )
  {
    super.onDisable( );
    
    PluginDescriptionFile description = this.getDescription( );
    this.log( description.getName( ) + " is now disabled." );
  }
}
