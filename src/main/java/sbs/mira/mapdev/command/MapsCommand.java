package sbs.mira.mapdev.command;

import app.ashcon.intake.Command;
import app.ashcon.intake.parametric.annotation.Switch;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sbs.mira.core.model.MiraCommandModel;
import sbs.mira.mapdev.MiraMapDevPulse;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * allows the instructor to list out all maps;
 * both locally and from the external "repo" directory.
 */
public
class MapsCommand
  extends MiraCommandModel<MiraMapDevPulse>
{
  public
  MapsCommand( @NotNull MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  @Command (
    aliases = { "maps" },
    usage = "[-r] [-p <page>] [-n <count>]",
    desc = "list all worlds locally or within the [-r] repo.",
    help = "no help yet. will write later.",
    perms = "mira.maps",
    flags = "rp:n:"
  )
  public
  void maps(
    @NotNull CommandSender sender,
    @Switch ('r') boolean with_repository,
    @Switch ('p') Integer page_number,
    @Switch ('n') Integer page_size
  )
  {
    boolean page_number_provided = page_number != null;
    int actual_page_number = page_number_provided ? page_number : 1;
    
    boolean page_size_provided = page_size != null;
    int actual_page_size = page_size_provided ? page_size : 10;
    
    if ( actual_page_number <= 0 )
    {
      sender.sendMessage( pulse( ).model( ).message( "error.number.greater_than_zero", "[-p] page number" ) );
      return;
    }
    
    if ( actual_page_size <= 0 )
    {
      sender.sendMessage( pulse( ).model( ).message( "error.number.greater_than_zero", "[-n] page size" ) );
      return;
    }
    
    int upper_map_count = actual_page_size * actual_page_number;
    int lower_map_index = upper_map_count - actual_page_size;
    
    File world_container = with_repository ?
      pulse( ).model( ).world( ).repo( ) :
      pulse( ).plugin( ).getServer( ).getWorldContainer( );
    
    List<String> worlds = Arrays
      .stream( Objects.requireNonNull( world_container.listFiles( ) ) )
      .filter( File::isDirectory )
      .filter( ( file )->pulse( ).model( ).files( ).is_world_directory( file ) )
      .map( File::getName )
      .toList( );
    
    if ( worlds.isEmpty( ) )
    {
      sender.sendMessage( pulse( ).model( ).message( "error.map.list.nothing" ) );
      return;
    }
    
    int max_page_modulo = worlds.size( ) % actual_page_size;
    int max_page_number = ( worlds.size( ) - max_page_modulo ) / actual_page_size;
    
    if ( max_page_modulo > 0 )
    {
      // additional page with less than a full page worth of maps.
      max_page_number++;
    }
    
    if ( actual_page_number > max_page_number )
    {
      sender.sendMessage(
        pulse( ).model( ).message(
          "error.map.list.max_page_exceeded",
          String.valueOf( max_page_number )
                                 )
      );
      return;
    }
    
    sender.sendMessage(
      pulse( ).model( ).message(
        "info.map.list.header",
        String.valueOf( actual_page_number ),
        String.valueOf( max_page_number ),
        String.valueOf( worlds.size( ) ),
        with_repository ? "[repo]" : ""
                               )
    );
    
    boolean reached_end = false;
    
    for ( int map_index = 0; map_index < actual_page_size; map_index++ )
    {
      int actual_map_index = lower_map_index + map_index;
      
      try
      {
        sender.sendMessage( pulse( ).model( ).message(
          "info.map.list.item",
          String.valueOf( actual_map_index + 1 ),
          worlds.get( actual_map_index )
                                                     ) );
      }
      catch ( IndexOutOfBoundsException exception )
      {
        reached_end = true;
        sender.sendMessage( pulse( ).model( ).message( "info.map.list.ends" ) );
        break;
      }
    }
    
    if ( !reached_end )
    {
      sender.sendMessage( pulse( ).model( ).message(
        "info.map.list.continues",
        String.valueOf( actual_page_number + 1 )
                                                   ) );
    }
  }
}
