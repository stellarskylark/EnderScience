package enderscience.github.io.blocks.tileentities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import enderscience.github.io.EnderScience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TeleporterTileEntity extends TileEntity {

	private NBTTagList teleporterlist = new NBTTagList();
	private String name;
	
	@Override
	public void writeToNBT(NBTTagCompound var1) {
		System.out.println("[Ender Science]: Teleporter NBT written");
		var1.setTag("teleporter", teleporterlist);
		if(name == null)
			name = "This Teleporter";
		var1.setString("name", name);
		super.writeToNBT(var1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound var1) {
		System.out.println("[Ender Science]: Teleporter NBT read");
		teleporterlist = var1.getTagList("teleporter", 8);
		name = var1.getString("name");
		if (name == null)
			name = "This Teleporter";
		super.readFromNBT(var1);
	}
	
	public void onTeleporterBreak(World world) {
		//Go through this teleporter's list of teleporters and delete this teleporter from their lists.
		for(int i = 0; i < teleporterlist.tagCount(); i++) {
			TeleporterTileEntity entity = (TeleporterTileEntity)world.getTileEntity(decodeCoordsBlockPos(teleporterlist.getStringTagAt(i)));
			if(entity != null) {
				entity.removeTeleporter(this.getPos());
				System.out.println("[Ender Science]: Teleporter coordinates removed");
			}
			else
				System.out.println("[Ender Science]: No TeleporterTileEntity at that location");
		}
	}
	
	public void removeTeleporter(BlockPos posIn) {
		String input = encodeCoords(posIn);
		//If the string for the coordinates of the block we are removing is the same as the one in the list, remove it. Otherwise, keep looking.
		for(int i = 0; i < teleporterlist.tagCount(); i++) {
			if(input.equals(teleporterlist.get(i))) {
				teleporterlist.removeTag(i);
				return;
			}
		}
	}
	
	public void addTeleporterList(NBTTagList list) {
		//Cycles through every item in the input list and calls addTeleporter()
		for(int i = 0; i < list.tagCount(); i++) {
			this.addTeleporter(list.getStringTagAt(i));
		}
	}
	
	public void addTeleporter(String input) {
		//Cycles through every other item in this list and looks for a match to the input. If it finds it, return. if it doesn't find it, it adds it.
		for(int i = 0; i < teleporterlist.tagCount(); i++) {
			String str = teleporterlist.getStringTagAt(i);
			if(str.equals(input))
				return;
		}
		teleporterlist.appendTag(new NBTTagString(input));
	}
	
	public void pairTeleporter(BlockPos posIn, World world, EntityPlayer player) {
		//Check if trying to pair the teleporter to itself
		if(encodeCoords(posIn).equals(encodeCoords(this.pos))) {
			if(!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentTranslation("msg.cannotpair.txt"));
			return;
		}
		
		//Adds the input teleporter to every teleporter this one knows about
		for(int i = 0; i < teleporterlist.tagCount(); i++) {
			Ticket ticket = ForgeChunkManager.requestTicket(EnderScience.instance, world, ForgeChunkManager.Type.NORMAL);
			ForgeChunkManager.forceChunk(ticket, world.getChunkFromBlockCoords(decodeCoordsBlockPos(teleporterlist.getStringTagAt(i))).getChunkCoordIntPair());
			((TeleporterTileEntity)world.getTileEntity(decodeCoordsBlockPos(teleporterlist.getStringTagAt(i)))).addTeleporter(encodeCoords(posIn));
			ForgeChunkManager.releaseTicket(ticket);
		}
		
		//Add this teleporter's list to the input's list, plus itself, and gets the teleporter list from the target.
		Ticket ticket = ForgeChunkManager.requestTicket(EnderScience.instance, world, ForgeChunkManager.Type.NORMAL);
		ForgeChunkManager.forceChunk(ticket, world.getChunkFromBlockCoords(posIn).getChunkCoordIntPair());
		if(world.getTileEntity(posIn) != null) {
			this.addTeleporterList(((TeleporterTileEntity)world.getTileEntity(posIn)).teleporterlist);
			((TeleporterTileEntity)world.getTileEntity(posIn)).addTeleporterList(teleporterlist);
			((TeleporterTileEntity)world.getTileEntity(posIn)).addTeleporter(encodeCoords(this.pos));
		}
		ForgeChunkManager.releaseTicket(ticket);
		
		//Adds the target
		this.addTeleporter(encodeCoords(posIn));
		if(!player.worldObj.isRemote)
			player.addChatMessage(new ChatComponentTranslation("msg.paired.txt"));
	}
	
	public void teleport(EntityPlayer player, World world) {
		//TODO Test code
		if(teleporterlist.tagCount() > 0) {
			int[] i = decodeCoordsIntArray(teleporterlist.getStringTagAt(new Random().nextInt(teleporterlist.tagCount())));
			Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(i[0], i[1], i[2]));
			if(!chunk.isLoaded()) {
				Ticket ticket = ForgeChunkManager.requestTicket(EnderScience.instance, world, ForgeChunkManager.Type.NORMAL);
				player.setPosition(i[0] + 0.5, i[1] + 1, i[2] + 0.5);
				ForgeChunkManager.releaseTicket(ticket);
			}
			else
				player.setPosition(i[0] + 0.5, i[1] + 1, i[2] + 0.5);
		}
		else
			if(!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentTranslation("msg.noteleporter.txt"));
	}
	
	/**
	 * Takes a string storing the coordinates of the target and decodes it into an int[] containing the coordinates
	 * @param s The string to decode
	 * @return The int[] containing the coordinates
	 */
	private int[] decodeCoordsIntArray(String s) {
		String coords[] = s.split("#");
		int[] i = new int[3];
		i[0] = Integer.parseInt(coords[0]);
		i[1] = Integer.parseInt(coords[1]);
		i[2] = Integer.parseInt(coords[2]);
		return i;
	}
	
	private BlockPos decodeCoordsBlockPos(String s) {
		String coords[]	= s.split("#");
		return new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
	}
	
	/**
	 * Takes a BlockPos and returns the string containing the coordinates
	 * @param pos The BlockPos to encode
	 * @return The encoded string
	 */
	private String encodeCoords(BlockPos pos) {
		return pos.getX() + "#" + pos.getY() + "#" + pos.getZ();
	}
	
	private String encodeCoords(int x, int y, int z) {
		return x + "#" + y + "#" + z;
	}
	
	public NBTTagList getList() {
		return teleporterlist;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String set) {
		name = set;
	}
}
