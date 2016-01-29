package enderscience.github.io.items;

import java.util.List;

import enderscience.github.io.EnderScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PunchCard extends Item {
	
	String[] names = {"blank", "written", "written"};
	
	public PunchCard()
	{
		setMaxStackSize(1);
		setCreativeTab(EnderScience.tab);
		setHasSubtypes(true);
		setMaxDamage(0);
		setUnlocalizedName("punchcard");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) 
	{
		if(stack.getMetadata() == 1 || stack.getMetadata() == 2)
			tooltip.add("Coordinates: " + stack.getTagCompound().getString("teleporter"));
	}
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + names[stack.getMetadata()];
    }
}
