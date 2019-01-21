package com.obcletter.industryexpansion.block;

import com.obcletter.industryexpansion.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	public static BlockOre hardened_redstone_ore = new BlockOre("hardened_redstone_ore", ModItems.raw_hardened_redstone)
			.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	public static BlockFreezer freezer = new BlockFreezer("freezer").setCreativeTab(CreativeTabs.REDSTONE);
	public static BlockRedMeter red_meter = new BlockRedMeter("red_meter").setCreativeTab(CreativeTabs.REDSTONE);

	public static void register(IForgeRegistry<Block> registry) {
		registry.registerAll(hardened_redstone_ore, freezer, red_meter);
		GameRegistry.registerTileEntity(freezer.getTileEntityClass(), freezer.getRegistryName().toString());
		GameRegistry.registerTileEntity(red_meter.getTileEntityClass(), red_meter.getRegistryName().toString());
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registry.registerAll(hardened_redstone_ore.createItemBlock(), freezer.createItemBlock(),
				red_meter.createItemBlock());
	}

	public static void registerModels() {
		hardened_redstone_ore.registerItemModel(Item.getItemFromBlock(hardened_redstone_ore));
		freezer.registerItemModel(Item.getItemFromBlock(freezer));
		red_meter.registerItemModel(Item.getItemFromBlock(red_meter));
	}
}
