package enderscience.github.io.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderScienceTab extends CreativeTabs {

	public EnderScienceTab(String label) {
		super(label);
	}

	@SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Items.ender_pearl;
    };
}
