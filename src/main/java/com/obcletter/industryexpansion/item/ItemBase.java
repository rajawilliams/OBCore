package com.obcletter.industryexpansion.item;

import com.obcletter.industryexpansion.IndustryExpansion;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

	protected String name;
	
	public ItemBase(String name) {
		this.name = name;
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	}
	
	public void registerItemModel() {
		IndustryExpansion.proxy.registerItemRenderer(this, 0, name);
	}
	
	public ItemBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
}
