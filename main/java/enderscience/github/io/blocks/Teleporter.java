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
		//Get the tile entity of this teleporter
		TeleporterTileEntity t = (TeleporterTileEntity) worldIn.getTileEntity(pos);
		
		//Check if they're holding an Wand of Ender (trying to pair the teleporters)
		ItemStack item = playerIn.getCurrentEquippedItem();
		if(item != null && item.getItem() == EnderScience.enderWand) {
			NBTTagCompound tag = item.getTagCompound();
			//If the tag doesn't exist
			if(tag == null) {
				tag = new NBTTagCompound();
				item.setTagCompound(tag);
			}
			
			//If there is no target teleporter stored in the Wand of Ender
			if(tag.getString("teleporterstored").equals("")) {
				tag.setString("teleporterstored", pos.getX() + "#" + pos.getY() + "#" + pos.getZ());
				if(!playerIn.worldObj.isRemote)
					playerIn.addChatMessage(new ChatComponentTranslation("msg.teleporterstored.txt"));
				return true;
			}
			else {
				String s = tag.getString("teleporterstored");
				String[] coords = s.split("#");
				t.pairTeleporter(new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])), worldIn, playerIn);
				tag.setString("teleporterstored", "");
				return true;
			}
		}
		else {
			playerIn.openGui(EnderScience.instance, TeleporterGUI.GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}
	
	@Override
	public int getRenderType()
    {
        return 3;
    }

}
