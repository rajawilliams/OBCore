package com.obcletter.industryexpansion.client;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.block.ModBlocks;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFreezer extends GuiContainer {

	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MOD_ID,
			"textures/gui/freezer.png");

	private static final String unlocalizedMinimum = ModBlocks.freezer.getUnlocalizedName() + ".minimum";

	private InventoryPlayer playerInv;

	public GuiFreezer(Container inventorySlotsIn, InventoryPlayer playerInv) {
		super(inventorySlotsIn);
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String minimum = I18n.format(unlocalizedMinimum);
		String name = I18n.format(ModBlocks.freezer.getUnlocalizedName() + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
		fontRenderer.drawString(minimum, xSize / 2 - fontRenderer.getStringWidth(minimum) / 2, 14, 0x404040);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
