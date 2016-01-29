package enderscience.github.io.blocks;

import enderscience.github.io.EnderScience;
import enderscience.github.io.blocks.tileentities.TeleporterTileEntity;
import enderscience.github.io.client.gui.TeleporterGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Teleporter extends BlockContainer {
	
	
	public Teleporter(Material materialIn) {
		super(materialIn);
		setCreativeTab(EnderScience.tab);
		setStepSound(Block.soundTypeStone);
		setUnlocalizedName("teleporter");
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TeleporterTileEntity();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TeleporterTileEntity t = (TeleporterTileEntity)worldIn.getTileEntity(pos);
		t.onTeleporterBreak(worldIn);
        super.breakBlock(worldIn, pos, state);
    }
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) //TODO: This check is best practice, but seems to break some stuff; it shouldn't once we switch to metadata for punch cards.
		{
			//Get the tile entity of this teleporter
			TeleporterTileEntity t = (TeleporterTileEntity) worldIn.getTileEntity(pos);
			//Check if they're holding a blank punch card
			ItemStack item = playerIn.getCurrentEquippedItem();
			if(item != null && item.getItem() == EnderScience.blankPunch) {
				//Replace with written card
				ItemStack card = new ItemStack(EnderScience.writtenPunch, 1);
				int slot = playerIn.inventory.currentItem;
				playerIn.replaceItemInInventory(slot, card);
				//Store this teleporter's coordinates in the new punch card
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("teleporter", pos.getX() + " " + pos.getY() + " " + pos.getZ());
				card.setTagCompound(tag);
				if(playerIn.getCurrentEquippedItem().getTagCompound() == null)
					System.out.println("[Ender Science]: Failed to write card's NBT.");
				System.out.println("[Ender Science]: Teleporter coordinates: " + tag.getString("teleporter"));
				return true;
			}
			else if (item != null && item.getItem() == EnderScience.writtenPunch){
				//TODO: Fix teleport call
				t.teleport(playerIn, worldIn, item.getTagCompound().getString("teleporter"));
				return true;
			}
		}
		return true;
	}
	
	@Override
	public int getRenderType()
    {
        return 3;
    }
}
