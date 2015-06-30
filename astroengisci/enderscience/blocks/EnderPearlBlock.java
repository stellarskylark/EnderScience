package astroengisci.enderscience.blocks;

import java.util.Random;

import astroengisci.enderscience.EnderScience;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class EnderPearlBlock extends Block {
	
	

	public EnderPearlBlock(Material materialIn) {
		super(materialIn);
		setHardness(1.0F);
		setStepSound(Block.soundTypeGlass);
		setUnlocalizedName("enderpearlblock");
		setCreativeTab(EnderScience.tab);
		setHarvestLevel("pickaxe", 1);
		setLightOpacity(3);
	}
	
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return Items.ender_pearl;
	}
	
	public int quantityDropped(Random random) {
		return 5 + random.nextInt(4);
	}

}
