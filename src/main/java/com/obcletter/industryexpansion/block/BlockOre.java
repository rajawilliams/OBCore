package com.obcletter.industryexpansion.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOre extends BlockBase {
	
	private Item drop;

	public BlockOre(String name) {
		super(Material.ROCK, name);
		this.drop = null;
	}
	
	public BlockOre(String name, Item drop) {
		super(Material.ROCK, name);
		this.drop = drop;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		if(drop != null) {
			drops.add(new ItemStack(drop,1));
		} else {
			super.getDrops(drops, world, pos, state, fortune);
		}
	}
	
	@Override
	public BlockOre setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

}
