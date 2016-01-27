package enderscience.github.io.items;

import enderscience.github.io.EnderScience;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class EndiumIngot extends Item {
	
	public EndiumIngot() {
		setMaxStackSize(64);
		setCreativeTab(EnderScience.tab);
		setUnlocalizedName("endiumingot");
	}
}
