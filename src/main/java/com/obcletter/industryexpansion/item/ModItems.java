package com.obcletter.industryexpansion.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	
	public static ItemBase machine_casing = new ItemBase("machine_casing").setCreativeTab(CreativeTabs.REDSTONE);
	public static ItemBase redstone_core = new ItemBase("redstone_core").setCreativeTab(CreativeTabs.REDSTONE);
	public static ItemBase raw_hardened_redstone = new ItemBase("raw_hardened_redstone").setCreativeTab(CreativeTabs.REDSTONE);

	public static void registerItems(IForgeRegistry<Item> registry) {
		registry.registerAll(
				machine_casing,
				redstone_core,
				raw_hardened_redstone
				);
	}
	
	public static void registerModels() {
		machine_casing.registerItemModel();
		redstone_core.registerItemModel();
		raw_hardened_redstone.registerItemModel();
	}
}
