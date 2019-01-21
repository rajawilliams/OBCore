package com.obcletter.industryexpansion;

import java.util.Random;

import com.obcletter.industryexpansion.block.ModBlocks;
import com.obcletter.industryexpansion.client.CommonProxy;
import com.obcletter.industryexpansion.client.ModGuiHandler;
import com.obcletter.industryexpansion.entity.ModEntities;
import com.obcletter.industryexpansion.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class IndustryExpansion {

	@Mod.Instance
	public static IndustryExpansion instance;

	public static Random rand = new Random();

	@SidedProxy(clientSide = Reference.CLIENTSIDE_PROXY, serverSide = Reference.SERVERSIDE_PROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
		proxy.init();
		ModEntities.init();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ModBlocks.register(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ModItems.registerItems(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			ModItems.registerModels();
			ModBlocks.registerModels();
		}
	}
}
