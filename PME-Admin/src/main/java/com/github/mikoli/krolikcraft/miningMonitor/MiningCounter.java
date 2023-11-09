package com.github.mikoli.krolikcraft.miningMonitor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashSet;

public class MiningCounter {

    private final HashSet<Location> counted = new HashSet<>();
    private final BlockFace[] horizontalFaces = new BlockFace[] { BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.DOWN, BlockFace.UP };
    private final BlockFace[] upperFaces = new BlockFace[] { BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.UP };
    private final BlockFace[] lowerFaces = new BlockFace[] { BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.DOWN };

    public MiningCounter() {}

    public int getTotalBlocks(Block original) {
        HashSet<Location> blocks = new HashSet<>();
        blocks.add(original.getLocation());
        cycleHorizontalFAces(original.getType(), original, blocks);
        return blocks.size();
    }

    private void cycleHorizontalFAces(Material material, Block original, HashSet<Location> blocks) {
        findBlockLike(this.horizontalFaces, original, material, blocks);
        Block upper = original.getRelative(BlockFace.UP);
        findBlockLike(this.upperFaces, upper, material, blocks);
        Block lower = original.getRelative(BlockFace.DOWN);
        findBlockLike(this.lowerFaces, lower, material, blocks);
    }

    private void findBlockLike(BlockFace[] faces, Block original, Material originalMaterial, HashSet<Location> blocks) {
        for (BlockFace face : faces) {
            Block block = original.getRelative(face);
            if ((block.getType().equals(originalMaterial) && !blocks.contains(block.getLocation())) && isAnnounced(block.getLocation())) {
                this.counted.add(block.getLocation());
                blocks.add(block.getLocation());
                cycleHorizontalFAces(originalMaterial, block, blocks);
            }
        }
    }
    public boolean wasCounted(Location location) {
        return this.counted.contains(location);
    }

    private boolean isAnnounced(Location location) {
        return !wasCounted(location);
    }

    private void removeAnnounced(Location location) {
        this.counted.remove(location);
    }
}
