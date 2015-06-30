package astroengisci.enderscience.items;

import astroengisci.enderscience.EnderScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EnderWand extends Item {
	
	public EnderWand() {
		setMaxStackSize(1);
		setUnlocalizedName("enderwand");
		setCreativeTab(EnderScience.tab);
	}
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    NBTTagCompound tag = itemStack.getTagCompound();
	    if(tag == null) {
	    	tag = new NBTTagCompound();
	    	itemStack.setTagCompound(tag);
	    }
	    tag.setString("teleporterstored", "");
	}
}
