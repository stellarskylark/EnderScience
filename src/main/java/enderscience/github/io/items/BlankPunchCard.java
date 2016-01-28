package enderscience.github.io.items;

import enderscience.github.io.EnderScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlankPunchCard extends Item {
	
	public BlankPunchCard()
	{
		setMaxStackSize(1);
		setUnlocalizedName("blankpunch");
		setCreativeTab(EnderScience.tab);
	}
}
