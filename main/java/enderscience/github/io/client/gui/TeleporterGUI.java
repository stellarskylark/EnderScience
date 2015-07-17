package enderscience.github.io.client.gui;

import java.io.IOException;
import java.util.List;

import enderscience.github.io.blocks.tileentities.TeleporterTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.GuiScrollingList;

public class TeleporterGUI extends GuiScreen {
	
	//TODO Finish writing the teleporter GUI
	public static final int GUI_ID = 52001;
	BlockPos position;
	World theWorld;
	EntityPlayer player;
	private ResourceLocation teleporterGui = new ResourceLocation("enderscience:textures/gui/teleporter.png");
	int xSize = 238;
	int ySize = 177;
	GuiScrollingList list;
	TeleporterTileEntity tileEntity;
	GuiTextField nameField;
	
	TeleporterGUI(BlockPos pos, World world, EntityPlayer player) {
		this.position = pos;
		this.theWorld = world;
		this.player = player;
		tileEntity = (TeleporterTileEntity)theWorld.getTileEntity(position);
	}
	
	@Override
    public void drawScreen(int par1, int par2, float par3) {
            this.drawDefaultBackground();
            this.mc.getTextureManager().bindTexture(teleporterGui);
            int x = (this.width - xSize) / 2;
            int y = (this.height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            
            nameField.drawTextBox();
            for (int i = 0; i < buttonList.size(); i++)
            {
            	((GuiButton)buttonList.get(i)).drawButton(this.mc, par1, par2);
            }
    }
	
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void initGui() {
    	super.initGui();
    	nameField = new GuiTextField(1, this.fontRendererObj, ((this.width - xSize) / 2) + 115, ((this.height - ySize) / 2) + 6, 115, 15);
    	nameField.setTextColor(9999992);
    	nameField.setDisabledTextColour(4210752);
    	nameField.setMaxStringLength(16);
    	nameField.setEnabled(true);
    	if(tileEntity.getName() == null)
    		tileEntity.setName("This Teleporter");
    	nameField.setText(tileEntity.getName());
    	//Add buttons
    	buttonList.add(new GuiButton(0, ((this.width - xSize) / 2) + ((xSize - 220) / 2) , (this.height - (ySize / 4) + 160) / 2, 220, 20, "Teleport"));
    	
    }
    
    protected void actionPerformed(GuiButton button) {
    	switch(button.id) {
    	case 0:
    		//Teleport
    	}
    }
    
    @Override
    public void updateScreen() {
    	nameField.updateCursorCounter();
    }
    
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		nameField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		nameField.textboxKeyTyped(typedChar, keyCode);
	}

	@Override
	public void onGuiClosed() {
		tileEntity.setName(nameField.getText());
	}
}

