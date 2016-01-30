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
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

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
		if(!worldIn.isRemote) //Inventory modification must happens server-side
		{
			//Check if they're holding a punch card
			ItemStack item = playerIn.getCurrentEquippedItem();
			if(item != null && item.getItem() == EnderScience.punchCard && item.getMetadata() == 0) {
				//Change to written card
				item.setItemDamage(1);
				
				//Store this teleporter's coordinates in the new punch card
				if(item.getTagCompound() == null)
					item.setTagCompound(new NBTTagCompound());
				item.getTagCompound().setString("teleporter", encodeCoords(pos));;
				if(playerIn.getCurrentEquippedItem().getTagCompound() == null)
					System.out.println("[Ender Science]: Failed to write card's NBT.");
				System.out.println("[Ender Science]: Teleporter coordinates: " + item.getTagCompound().getString("teleporter"));
				playerIn.addChatMessage(new ChatComponentTranslation("msg.teleporterstored.txt"));
				return true;
			}
			//If clicking with a written punch card and the target teleporter doesn't exist
			else if((item != null && item.getItem() == EnderScience.punchCard && (item.getMetadata() == 1 || item.getMetadata() == 2)))
			{
				if(worldIn.getTileEntity(decodeCoordsBlockPos(item.getTagCompound().getString("teleporter"))) == null
					|| worldIn.getTileEntity(decodeCoordsBlockPos(item.getTagCompound().getString("teleporter"))).getBlockType() != EnderScience.teleporter)
				{
					//Delete punchcard if teleporter doesn't exist.
					playerIn.addChatMessage(new ChatComponentTranslation("msg.teleporternotexist.txt"));
					item.stackSize = 0;
					return true;
				}
				else if(decodeCoordsBlockPos(item.getTagCompound().getString("teleporter")).equals(pos))
				{
					playerIn.addChatMessage(new ChatComponentTranslation("msg.sameteleporter.txt"));
					return true;
				}
				teleport(playerIn, worldIn, item.getTagCompound().getString("teleporter"));
			}
		}
		return true;
	}
	
	@Override
	public int getRenderType()
    {
        return 3;
    }
	
	/**
	 * Takes a BlockPos and returns the string containing the coordinates
	 * @param pos The BlockPos to encode
	 * @return The encoded string
	 */
	private String encodeCoords(BlockPos pos) {
		return pos.getX() + " " + pos.getY() + " " + pos.getZ();
	}
	
	/**
	 * Takes a string storing the coordinates of the target and decodes it into an int[] containing the coordinates
	 * @param s The string to decode
	 * @return The int[] containing the coordinates
	 */
	private int[] decodeCoordsIntArray(String s) {
		String coords[] = s.split(" ");
		int[] i = new int[3];
		i[0] = Integer.parseInt(coords[0]);
		i[1] = Integer.parseInt(coords[1]);
		i[2] = Integer.parseInt(coords[2]);
		return i;
	}
	
	private BlockPos decodeCoordsBlockPos(String s) {
		String coords[]	= s.split(" ");
		return new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
	}
	
	public void teleport(EntityPlayer player, World world, String target) {
		int[] i = decodeCoordsIntArray(target);
		Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(i[0], i[1], i[2]));
		if(!chunk.isLoaded()) {
			Ticket ticket = ForgeChunkManager.requestTicket(EnderScience.instance, world, ForgeChunkManager.Type.NORMAL);
			player.setPositionAndUpdate(i[0] + 0.5, i[1] + 1, i[2] + 0.5);
			ForgeChunkManager.releaseTicket(ticket);
		}
		player.setPositionAndUpdate(i[0] + 0.5, i[1] + 1, i[2] + 0.5);
	}
	
	
}
