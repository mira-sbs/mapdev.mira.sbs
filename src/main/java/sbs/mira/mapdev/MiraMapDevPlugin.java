package sbs.mira.mapdev;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import org.bukkit.plugin.PluginDescriptionFile;
import sbs.mira.core.MiraPlugin;
import sbs.mira.mapdev.command.MapCommand;
import sbs.mira.mapdev.command.MapsCommand;

import java.util.logging.Logger;

public
class MiraMapDevPlugin
  extends MiraPlugin<MiraMapDevPulse>
{
  
  public final Logger logger = Logger.getLogger( "Minecraft" );
  
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
    
    pulse( ).breathe( this, new MiraMapDevMaster( pulse( ) ) );
    
    BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph( );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapCommand( pulse( ) ) );
    cmdGraph.getRootDispatcherNode( ).registerCommands( new MapsCommand( pulse( ) ) );
    
    BukkitIntake intake = new BukkitIntake( this, cmdGraph );
    
    intake.register( );
    PluginDescriptionFile pdfFile = getDescription( );
    
    this.logger.info( pdfFile.getName( ) + " version " + pdfFile.getVersion( ) + " is now loaded" );
  }
  
  @Override
  public
  void onEnable( )
  {
    super.onEnable( );
    
    PluginDescriptionFile pdfFile = getDescription( );
    this.logger.info( pdfFile.getName( ) + " version " + pdfFile.getVersion( ) + " is now enabled" );
  }
  
  public
  void onDisable( )
  {
    PluginDescriptionFile pdfFile = getDescription( );
    this.logger.info( pdfFile.getName( ) + " is now disabled" );
  }
}
