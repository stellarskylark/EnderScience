package enderscience.github.io.items;

import java.util.List;

import enderscience.github.io.EnderScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

//TODO: Put written, blank, and one-time-use cards all in one class, distinguished through metadata.
public class WrittenPunchCard extends Item {
	public WrittenPunchCard()
	{
		setMaxStackSize(1);
		setUnlocalizedName("writtenpunch");
		setCreativeTab(EnderScience.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) 
	{
		tooltip.add("Coordinates: " + stack.getTagCompound().getString("teleporter"));
	}
}
