//package com.github.mikoli.krolikcraft.listeners;
//
//import com.github.mikoli.krolikcraft.PMEFactions;
//import com.github.mikoli.krolikcraft.claims.ClaimsManager;
//import org.bukkit.block.Block;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.block.*;
//
//import java.util.UUID;
//
//public class OtherListeners implements Listener {
//
//    private final PMEFactions plugin;
//
//    public OtherListeners(PMEFactions plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onBlockBurnEvent(BlockBurnEvent event) {
//        Block block = event.getBlock();
//        ClaimsManager claimsManager = plugin.getClaimsManager();
//        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
//
//        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
//        if (claimsManager.getClaimsList().get(claimId).getCoreLocation() == block.getLocation()) event.setCancelled(true);
//    }
//
//    @EventHandler
//    public void onBlockExplodeEvent(BlockExplodeEvent event) {
//        Block block = event.getBlock();
//        ClaimsManager claimsManager = plugin.getClaimsManager();
//        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
//
//        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
//        if (claimsManager.getClaimsList().get(claimId).getCoreLocation() == block.getLocation()) event.setCancelled(true);
//    }
//
//    @EventHandler
//    public void onBlockFadeEvent(BlockFadeEvent event) {
//        Block block = event.getBlock();
//        ClaimsManager claimsManager = plugin.getClaimsManager();
//        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
//
//        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
//        if (claimsManager.getClaimsList().get(claimId).getCoreLocation() == block.getLocation()) event.setCancelled(true);
//    }
//
//    @EventHandler
//    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
//        Block block = event.getBlock();
//        ClaimsManager claimsManager = plugin.getClaimsManager();
//        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
//
//        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
//        if (event.getBlocks().contains(block.getWorld().getBlockAt(claimsManager.getClaimsList().get(claimId).getCoreLocation()))) event.setCancelled(true);
//    }
//
//    @EventHandler
//    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
//        Block block = event.getBlock();
//        ClaimsManager claimsManager = plugin.getClaimsManager();
//        if (!claimsManager.isChunkClaimed(block.getChunk())) return;
//
//        UUID claimId = claimsManager.getClaimId(event.getBlock().getChunk());
//        if (event.getBlocks().contains(block.getWorld().getBlockAt(claimsManager.getClaimsList().get(claimId).getCoreLocation()))) event.setCancelled(true);
//    }
//}
