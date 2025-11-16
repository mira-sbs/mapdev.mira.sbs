package sbs.mira.mapdev;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import sbs.mira.core.MiraPlugin;
import sbs.mira.mapdev.command.MapCommand;
import sbs.mira.mapdev.command.MapsCommand;
import sbs.mira.mapdev.command.TerraformCommand;

public
class MiraMapDevPlugin
  extends MiraPlugin<MiraMapDevPulse>
{
  @NotNull
  private final MiraMapDevModel model;
  
  public
  MiraMapDevPlugin( )
  {
    super( new MiraMapDevPulse( ) );
    
    this.model = new MiraMapDevModel( this.pulse( ) );
  }
  
  @Override
  public
  void onLoad( )
  {
    BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph( );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapCommand( this.pulse( ) ) );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapsCommand( this.pulse( ) ) );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new TerraformCommand( this.pulse( ) ) );
    
    BukkitIntake intake = new BukkitIntake( this, cmdGraph );
    intake.register( );
  }
  
  @Override
  public
  void onEnable( )
  {
    this.pulse( ).revive( this, this.model );
    
    this.pulse( ).log( "[mapdev] %s enables..".formatted( this.description( ) ) );
  }
}
