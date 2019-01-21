package com.obcletter.industryexpansion.block;

import com.obcletter.industryexpansion.IndustryExpansion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {
	
	protected String name;

	public BlockBase(Material materialIn, String name) {
		super(materialIn);
		this.name = name;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
	
	public void registerItemModel(Item itemBlock) {
		IndustryExpansion.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(this.getRegistryName());
	}
	
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

}
