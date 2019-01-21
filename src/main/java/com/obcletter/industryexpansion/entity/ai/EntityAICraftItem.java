package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityAICraftItem extends EntityAIBase {

	private World world;
	private EntitySandCivillian soldier;
	private Item itemToFind;
	private int count;

	private Item out;

	public EntityAICraftItem(@Nonnull EntitySandCivillian entity, World world, Item item, int count, Item out) {
		this.world = world;
		this.soldier = entity;
		this.itemToFind = item;
		this.count = count;
		this.out = out;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		if (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == itemToFind) {
			if (soldier.getHeldItem(EnumHand.MAIN_HAND).getCount() == count) {
				output = true;
			}
		}
		return output;
	}

	@Override
	public void startExecuting() {
		soldier.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(out));
	}

}
