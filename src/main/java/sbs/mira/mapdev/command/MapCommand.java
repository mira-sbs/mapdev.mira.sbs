package sbs.mira.mapdev.command;

import app.ashcon.intake.Command;
import app.ashcon.intake.parametric.annotation.Switch;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sbs.mira.core.model.MiraCommandModel;
import sbs.mira.core.utility.MiraWorldUtility;
import sbs.mira.mapdev.MiraMapDevPulse;

import java.io.File;
import java.io.IOException;

public
class MapCommand
  extends MiraCommandModel<MiraMapDevPulse>
{
  
  public
  MapCommand( MiraMapDevPulse pulse )
  {
    super( pulse );
  }
  
  private static final char FLAG_LOAD = 'l';
  private static final char FLAG_SAVE = 's';
  private static final char FLAG_TELEPORT = 't';
  private static final char FLAG_CREATE = 'c';
  private static final char FLAG_REPOSITORY = 'r';
  private static final char FLAG_UNLOAD = 'u';
  private static final char FLAG_DISCARD = 'd';
  
  @Command (
    aliases = { "map" },
    usage = "<label> [-l/load] [-t/teleport] [-r/from+to repository <repo map label>] [-u/unload] [-d/discard]",
    desc = "provides management for maps and their world files.",
    help = "help tbd.",
    perms = "mira.map",
    flags = "lcstr:ud"
  )
  @SuppressWarnings ("unused")
  public
  void map(
    @NotNull CommandSender sender,
    @NotNull String world_label,
    @Switch (FLAG_LOAD) boolean wants_load,
    @Switch (FLAG_SAVE) boolean wants_save,
    @Switch (FLAG_TELEPORT) boolean wants_teleport,
    @Switch (FLAG_CREATE) boolean wants_create,
    @Switch (FLAG_REPOSITORY) String repository_world_label,
    @Switch (FLAG_UNLOAD) boolean wants_unload,
    @Switch (FLAG_DISCARD) boolean wants_discard
          )
  {
    @NotNull File world_directory = new File(
      pulse( ).plugin( ).getServer( ).getWorldContainer( ),
      world_label
    );
    @Nullable World world = pulse( ).plugin( ).getServer( ).getWorld( world_label );
    boolean world_loaded = world != null;
    boolean with_repository = repository_world_label != null;
    
    // pre-validations for conflicting instructions, i.e. unload/discard + load/save.
    if ( ( wants_unload || wants_discard ) )
    {
      String conflicting_flags = "";
      
      if ( wants_load )
      {
        conflicting_flags += "l";
      }
      
      if ( wants_save )
      {
        conflicting_flags += "s";
      }
      
      if ( !conflicting_flags.isEmpty( ) )
      {
        sender.sendMessage( pulse( ).model( )
          .message( "error.flag_conflict", "ud", conflicting_flags ) );
        return;
      }
    }
    
    // pre-validation for attempts to discard worlds that are already loaded without the [-u] unload flag.
    if ( wants_discard && world_loaded && !wants_unload )
    {
      sender.sendMessage( pulse( ).model( )
        .message( "error.map.world.local.discard_unsafe", world_label ) );
      return;
    }
    
    if ( wants_save && !world_loaded )
    {
      sender.sendMessage( pulse( ).model( )
        .message(
          "error.map.world.local.not_loaded",
          world_label,
          "you cannot [-s] save it."
                ) );
      return;
    }
    
    if ( wants_load && world_loaded )
    {
      sender.sendMessage( pulse( ).model( )
        .message( "error.map.world.local.already_loaded", world_label ) );
      return;
    }
    
    // pre-validations for illegal [-r] repo flag usage.
    if ( with_repository )
    {
      if ( wants_create )
      {
        sender.sendMessage( pulse( ).model( )
          .message( "error.map.world.repo.with_create", world_label ) );
        return;
      }
      
      if ( !wants_load && !wants_save )
      {
        sender.sendMessage( pulse( ).model( ).message( "error.map.world.repo.ambiguity" ) );
        return;
      }
    }
    
    // maps loaded from the repository do not require the [-c] create flag.
    if ( wants_create && !wants_load )
    {
      sender.sendMessage( pulse( ).model( )
        .message( "error.map.world.local.create.without_load" ) );
      return;
    }
    
    if ( !wants_create && wants_load && !world_directory.exists( ) && !with_repository )
    {
      sender.sendMessage( pulse( ).model( )
        .message( "error.map.world.local.not_created", world_label ) );
      return;
    }
    
    // pre-validations for illegal [-t] teleport flag usage.
    if ( wants_teleport )
    
    {
      if ( wants_unload || wants_discard )
      {
        sender.sendMessage( pulse( ).model( )
          .message( "error.map.world.local.teleport_illegal" ) );
        return;
      }
      
      if ( !( sender instanceof LivingEntity ) )
      {
        sender.sendMessage( pulse( ).model( )
          .message( "error.map.world.local.teleport_nonplayer" ) );
        return;
      }
    }
    
    // execution of [-r] repository flag instructions - which take first priority.
    if ( with_repository )
    {
      if ( wants_load )
      {
        try
        {
          if ( !MiraWorldUtility.remembers(
            this.pulse( ).model( ).maps_repository( ).getAbsolutePath( ),
            repository_world_label,
            world_label ) )
          {
            sender.sendMessage( pulse( ).model( )
              .message(
                "error.map.world.repo.not_found",
                repository_world_label
                      ) );
            return;
          }
          
          sender.sendMessage( pulse( ).model( )
            .message(
              "info.map.world.repo.copied_from",
              repository_world_label,
              world_label
                    ) );
          
          // `return` not called here - it must also be loaded.
          assert wants_load;
        }
        catch ( IOException e )
        {
          sender.sendMessage( pulse( ).model( ).message( "error.generic", e.getMessage( ) ) );
          return;
        }
      }
      
      if ( wants_save )
      {
        try
        {
          MiraWorldUtility.stores(
            world,
            this.pulse( ).model( ).maps_repository( ).getAbsolutePath( ),
            repository_world_label,
            true );
          
          sender.sendMessage( pulse( ).model( )
            .message(
              "info.map.world.repo.copied_to",
              world_label,
              repository_world_label
                    ) );
          
          do_teleport( sender, wants_teleport, world, world_label );
          
          return;
        }
        catch ( IOException e )
        {
          sender.sendMessage( pulse( ).model( ).message( "error.generic", e.getMessage( ) ) );
          return;
        }
      }
    }
    else
    
    {
      if ( wants_save )
      {
        world.save( );
        
        sender.sendMessage( pulse( ).model( )
          .message( "info.map.world.local.saved", world_label ) );
        
        do_teleport( sender, wants_teleport, world, world_label );
        
        return;
      }
    }
    
    if ( wants_load )
    
    {
      world = MiraWorldUtility.loads( world_label, true );
      
      sender.sendMessage( pulse( ).model( )
        .message( "info.map.world.local.loaded", world_label ) );
      
      do_teleport( sender, wants_teleport, world, world_label );
      
      return;
    }
    
    if ( wants_teleport )
    {
      if ( !world_loaded )
      {
        sender.sendMessage( pulse( ).model( )
          .message(
            "error.map.world.local.not_loaded",
            world_label,
            "you cannot [-t] teleport into it."
                  ) );
        return;
      }
      
      do_teleport( sender, true, world, world_label );
      return;
    }
    
    if ( wants_discard )
    {
      try
      {
        MiraWorldUtility.discards( world_label );
      }
      catch ( IOException exception )
      {
        sender.sendMessage( pulse( ).model( )
          .message( "error.command.generic", exception.getMessage( ) ) );
        return;
      }
      
      sender.sendMessage( pulse( ).model( )
        .message( "info.map.world.local.unloaded", world_label ) );
      sender.sendMessage( pulse( ).model( )
        .message( "info.map.world.local.discarded", world_label ) );
      
      return;
    }
    else if ( wants_unload )
    
    {
      if ( !world_loaded )
      {
        sender.sendMessage( pulse( ).model( )
          .message(
            "error.map.world.local.not_loaded",
            world_label,
            "you cannot [-u] unload it."
                  ) );
        return;
      }
      
      MiraWorldUtility.unloads( world, true );
      
      sender.sendMessage( pulse( ).model( )
        .message( "info.map.world.local.unloaded", world_label ) );
      
      return;
    }
    
    sender.sendMessage( ChatColor.DARK_RED + "idk how u got here. but i will soon!" );
  }
  
  /**
   * use this method to handle [-t] teleport flag usage if
   *
   * @param sender         the entity that executed the calling command.
   * @param wants_teleport true - if the [-t] teleport flag was provided.
   * @param world          the world to teleport into - must always be loaded.
   * @param world_label    the world label provided to the command.
   */
  private
  void do_teleport(
    @NotNull CommandSender sender,
    boolean wants_teleport,
    @NotNull World world,
    @NotNull String world_label
                  )
  {
    if ( !wants_teleport )
    {
      return;
    }
    
    LivingEntity living_sender = ( LivingEntity ) sender;
    
    living_sender.getWorld( ).playSound(
      living_sender,
      Sound.ENTITY_ENDERMAN_TELEPORT,
      SoundCategory.PLAYERS,
      1,
      1
                                       );
    living_sender.teleport( new Location( world, 0, 64, 0 ) );
    living_sender.getWorld( ).playSound(
      living_sender,
      Sound.ENTITY_ENDERMAN_TELEPORT,
      SoundCategory.PLAYERS,
      1,
      1
                                       );
    
    sender.sendMessage( pulse( ).model( )
      .message( "info.player.teleported.voluntary", world_label ) );
    
  }
}
