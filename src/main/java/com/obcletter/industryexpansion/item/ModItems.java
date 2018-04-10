package com.obcletter.industryexpansion.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ModItems {
	
	public static ItemBase machine_casing = new ItemBase("machine_casing").setCreativeTab(CreativeTabs.REDSTONE);

	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				machine_casing
				);
	}
	
	public static void registerModels() {
		machine_casing.registerItemModel();
	}
}
