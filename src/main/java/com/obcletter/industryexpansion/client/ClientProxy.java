package com.obcletter.industryexpansion.client;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.client.renderer.RendererSandCivillian;
import com.obcletter.industryexpansion.client.renderer.RendererSandSoldier;
import com.obcletter.industryexpansion.entity.EntitySandCivillian;
import com.obcletter.industryexpansion.entity.EntitySandSoldier;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySandCivillian.class, RendererSandCivillian.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntitySandSoldier.class, RendererSandSoldier.FACTORY);
	}

	@Override
	public void registerItemRenderer(Item item, int metadata, String name) {
		ModelLoader.setCustomModelResourceLocation(item, metadata,
				new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory"));
	}

}