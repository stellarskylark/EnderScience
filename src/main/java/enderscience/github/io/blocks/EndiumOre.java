package enderscience.github.io.blocks;

import enderscience.github.io.EnderScience;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class EndiumOre extends Block {

	public EndiumOre(Material materialIn) {
		super(materialIn);

		setCreativeTab(EnderScience.tab);
		setStepSound(Block.soundTypeStone);
		setHarvestLevel("pickaxe", 2);
		setHardness(2.5F);
		setUnlocalizedName("endiumore");
	}

}
