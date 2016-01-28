package enderscience.github.io.items;

import enderscience.github.io.EnderScience;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class WrittenPunchCard extends Item {
	public WrittenPunchCard()
	{
		setMaxStackSize(1);
		setUnlocalizedName("writtenpunch");
		setCreativeTab(EnderScience.tab);
	}
}
