package astroengisci.enderscience;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class EnderScienceWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		switch(world.provider.getDimensionId()) {
		case -1:
			generateNether(world, random, (chunkX * 16) + random.nextInt(16), (chunkZ * 16) + random.nextInt(16));
		case 0:
			generateSurface(world, random, (chunkX * 16) + random.nextInt(16), (chunkZ * 16) + random.nextInt(16));
		case 1:
			generateEnd(world, random, (chunkX * 16) + random.nextInt(16), (chunkZ * 16) + random.nextInt(16));
		}

	}

	private void generateEnd(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	private void generateSurface(World world, Random random, int i, int j) {
		//Register overworld ores
		this.addOreSpawn(EnderScience.endiumOre, world, random, i, j, 6, 5, 55, 8);
		
	}

	private void generateNether(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Adds an ore spawn to Minecraft. Simply register all ores to spawn with this method in the generate() method of this class.
	 * 
	 * @param block Block to spawn in
	 * @param world World to spawn in
	 * @param random Random object for retrieving random positions within the world
	 * @param blockXPos int for passing the X-coordinate for generate()
	 * @param blockZPos int for passing the Z-coordinate for generate()
	 * @param maxVeinSize int for setting the maximum size of a vein
	 * @param chancesToSpawn int for the number of chances for the Block to spawn per-chunk
	 * @param minY int for the minimum Y-coordinate height at which this block may spawn
	 * @param maxY int for the maximum Y-coordinate height at which this block may spawn
	 **/
	public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, 
			int maxVeinSize, int minY, int maxY, int chancesToSpawn) {
		
		//Set up the necessary variables
		assert maxY > minY: "addOreSpawn: The maximum Y must be greater than the minimum Y";
		assert maxY > 0: "addOreSpawn: The minimum Y must be greater than 0";
		assert maxY < 256 && maxY > 0: "addOreSpawn: The maximum Y must be less than 256 and greater than 0";
		
		int diffBtwnMinMaxY = maxY - minY;
		int posY = minY + random.nextInt(diffBtwnMinMaxY);
		
		//Create WorldGenMinable for the new ore
		WorldGenMinable gen = new WorldGenMinable(block.getDefaultState(), maxVeinSize);
		
		for(int i = 0; i < chancesToSpawn; i++) {
			gen.generate(world, random, new BlockPos(blockXPos, posY, blockZPos));
		}
	}
}
