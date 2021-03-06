package org.bapcraft.glowingbroccoli;

import java.util.Optional;

import org.bapcraft.glowingbroccoli.config.GbLobbyConfig;
import org.bapcraft.glowingbroccoli.config.GbRootConfig;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class GbListener {

	private GbRootConfig config;
	private Game game;
	private Logger logger;
	
	public GbListener(GbRootConfig c, Game g, Logger l) {
		this.config = c;
		this.game = g;
		this.logger = l;
	}
	
	@Listener
	public void onPlayerMove(MoveEntityEvent event) {
		
		Entity e = event.getTargetEntity();
		
		if (e instanceof Player) {
			
			Player p = (Player) e;
			World w = p.getLocation().getExtent();
			
			for (GbLobbyConfig lc : this.config.worldConfigs) {
				
				if (w.getName().equals(lc.world) && p.getLocation().getBlockY() < lc.teleportHeight) {
					
					// TODO Actually look up their spawn and teleport them.
					
					if (false /* TODO */) {
						
						Location<World> sl = null;
						p.setLocationSafely(sl);
						
					} else {
						
						this.logger.info("Generating new spawn location for player " + p.getName());
						this.logger.error("NOT YET IMPLEMENTED!");
						
						// TODO Make new spawn data and attach it to the player.
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@Listener
	public void onPlayerDeath(DestructEntityEvent.Death event) {
		
		Entity e = event.getTargetEntity();
		
		if (e instanceof Player) {
			
			Player p = (Player) e;
			Location<World> l = p.getLocation();
			World w = l.getExtent();
			
			for (GbLobbyConfig lc : this.config.worldConfigs) {
				
				if (lc.playWorlds.contains(w.getName())) {
					
					Optional<World> ow = this.game.getServer().getWorld(lc.world);
					if (ow.isPresent()) {
						
						// TODO See how this works with the `/back` command.
						
					} else {
						this.logger.warn("Can't find lobby world " + lc.world + " to teleport player " + p.getName() + " to!");
					}
					
				}
				
			}
			
		}
		
	}
	
}
