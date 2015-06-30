package astroengisci.enderscience.client.gui;

import astroengisci.enderscience.blocks.tileentities.TeleporterTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TeleporterGUI extends GuiScreen {
	
	//TODO Finish writing the teleporter GUI
	public static final int GUI_ID = 52001;
	BlockPos position;
	World theWorld;
	EntityPlayer player;

	TeleporterGUI(BlockPos pos, World world, EntityPlayer player) {
		position = pos;
		theWorld = world;
		this.player = player;
	}
	
	@Override
    public void drawScreen(int par1, int par2, float par3) {
            this.drawDefaultBackground();
    }
	
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void initGui() {
    	super.initGui();
    	buttonList.add(new GuiButton(1, 100, 100, 100, 100, "Teleport"));
    	//Add buttons
    }
    
    protected void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 1:
    		TeleporterTileEntity entity = (TeleporterTileEntity) theWorld.getTileEntity(position);
    		entity.teleport(player, theWorld);
    	}
    }
}

