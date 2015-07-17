package enderscience.github.io;

import enderscience.github.io.blocks.*;
import enderscience.github.io.blocks.tileentities.TeleporterTileEntity;
import enderscience.github.io.client.gui.EnderScienceGUI;
import enderscience.github.io.items.*;
import enderscience.github.io.tab.EnderScienceTab;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

@Mod(modid="enderscience", name="Ender Science", version="0.0.0")
public class EnderScience {
	
	public static CreativeTabs tab = new EnderScienceTab("enderScience");
	
	//Block declarations
	public static Block enderPearlBlock = new EnderPearlBlock(Material.rock);
	public static Block endiumOre = new EndiumOre(Material.rock);
	public static Block teleporter = new Teleporter(Material.rock);
	
	//Item declarations
	public static Item endiumIngot = new EndiumIngot();
	public static Item enderWand = new EnderWand();
	
	//Frequent ItemStack references here for microoptimization in recipe creation
	ItemStack enderPearlStack = new ItemStack(Items.ender_pearl);
	ItemStack endiumIngotStack = new ItemStack(endiumIngot);
	
	//The instance that Forge uses.
	@Instance(value = "enderscience")
	public static EnderScience instance;
	
	//Where the client and server 'proxy' code is loaded
	@SidedProxy(clientSide="enderscience.github.io.client.ClientProxy", serverSide="enderscience.github.io.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Register world generator
		GameRegistry.registerWorldGenerator(new EnderScienceWorldGen(), 1);
		
		//Register blocks
		GameRegistry.registerBlock(enderPearlBlock, "enderpearlblock");
		GameRegistry.registerBlock(endiumOre, "endiumore");
		GameRegistry.registerBlock(teleporter, "teleporter");
		
		//Register items
		GameRegistry.registerItem(endiumIngot, "endiumingot");
		GameRegistry.registerItem(enderWand, "enderwand");
		
		//Register crafting recipes
		GameRegistry.addShapelessRecipe(new ItemStack(enderPearlBlock),
				enderPearlStack, enderPearlStack, enderPearlStack,
				enderPearlStack, enderPearlStack, enderPearlStack,
				enderPearlStack, enderPearlStack, enderPearlStack);
		
		//Register smelting recipes
		GameRegistry.addSmelting(endiumOre, endiumIngotStack, 0.7F);
		
		//Register tile entities
		GameRegistry.registerTileEntity(enderscience.github.io.blocks.tileentities.TeleporterTileEntity.class, "teleporterTileEntity");
		
		//Register renders
		if(event.getSide() == Side.CLIENT)
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			
			//Blocks
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(enderPearlBlock), 0, new ModelResourceLocation("enderscience:enderpearlblock", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(endiumOre), 0, new ModelResourceLocation("enderscience:endiumore", "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(teleporter), 0, new ModelResourceLocation("enderscience:teleporter", "inventory"));
			
			//Items
			renderItem.getItemModelMesher().register(endiumIngot, 0, new ModelResourceLocation("enderscience:endiumingot", "inventory"));
		}
		
		//Register GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new EnderScienceGUI());
	};
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new EnderScienceChunkload());
	}
}
