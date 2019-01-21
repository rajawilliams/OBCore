package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAISmeltItems extends EntityAIBase {

	private World world;
	private EntitySandCivillian soldier;
	private float speedIn;
	private Item stored;
	private int slot;

	public EntityAISmeltItems(@Nonnull EntitySandCivillian entity, World world, float speedIn, Item stored, int slot) {
		this.world = world;
		this.soldier = entity;
		this.speedIn = speedIn;
		this.stored = stored;
		this.slot = slot;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		int max = 100000;
		if (soldier.hasResidence()) {
			if (soldier.getIndustrialage() > 1) {
				if (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == stored) {
					soldier.wantsToGoTo = soldier.getFurnace().getPos();
					output = true;
				}
			}
		}
		return output;
	}

	@Override
	public void updateTask() {
		if (soldier.wantsToGoTo != BlockPos.NULL_VECTOR) {
			soldier.getNavigator().tryMoveToXYZ(soldier.wantsToGoTo.getX(), soldier.wantsToGoTo.getY(),
					soldier.wantsToGoTo.getZ(), speedIn);
			double distance = soldier.getPosition().getDistance(soldier.wantsToGoTo.getX(), soldier.wantsToGoTo.getY(),
					soldier.wantsToGoTo.getZ());
			if (distance < 2) {
				TileEntityFurnace furnace = soldier.getFurnace();
				if (furnace.isItemValidForSlot(slot, soldier.getHeldItem(EnumHand.MAIN_HAND))) {
					if (furnace.getStackInSlot(slot).isEmpty()) {
						furnace.setInventorySlotContents(slot, soldier.getHeldItem(EnumHand.MAIN_HAND));
						soldier.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
					} else if (furnace.getStackInSlot(slot).getItem() == soldier.getHeldItem(EnumHand.MAIN_HAND)
							.getItem()) {
						furnace.setInventorySlotContents(slot,
								new ItemStack(furnace.getStackInSlot(slot).getItem(),
										furnace.getStackInSlot(slot).getCount()
												+ soldier.getHeldItem(EnumHand.MAIN_HAND).getCount()));
						soldier.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
					}

				}
			}
		}
	}

	@Override
	public void resetTask() {
		soldier.wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);
	}

}
