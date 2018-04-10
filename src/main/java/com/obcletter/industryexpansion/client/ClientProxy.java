package com.obcletter.industryexpansion.client;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.item.ItemBase;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
	}
	
	@Override
	public void registerItemRenderer(ItemBase item, int metadata, String name) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory"));
	}

}