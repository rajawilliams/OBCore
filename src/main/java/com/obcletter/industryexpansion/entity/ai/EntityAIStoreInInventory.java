package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EntityAIStoreInInventory extends EntityAIBase {

	private World world;
	private EntitySandCivillian soldier;
	private float speedIn;
	private Item stored;

	private boolean actionDone = true;

	public EntityAIStoreInInventory(@Nonnull EntitySandCivillian entity, World world, float speedIn, Item stored) {
		this.world = world;
		this.soldier = entity;
		this.speedIn = speedIn;
		this.stored = stored;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		int max = 100000;
		if (soldier.hasResidence()) {
			if (actionDone) {
				if (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == stored) {
					if (soldier.getResourceChest() != null) {
						soldier.wantsToGoTo = soldier.getResourceChest().getPos();
						output = true;
						actionDone = false;
					}
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
			if (distance < 3) {
				TileEntityChest chestEntity = soldier.getResourceChest();
				IItemHandler handlerChest = chestEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
						null);
				int freeSlot = findFirstEmptySlot(chestEntity, soldier.getHeldItem(EnumHand.MAIN_HAND).getItem());
				if (freeSlot != -1) {
					if (!actionDone) {
						if (chestEntity.getStackInSlot(freeSlot).isEmpty()) {
							chestEntity.setInventorySlotContents(freeSlot, soldier.getHeldItem(EnumHand.MAIN_HAND));
							soldier.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
						} else if (chestEntity.getStackInSlot(freeSlot).getItem() == soldier
								.getHeldItem(EnumHand.MAIN_HAND).getItem()) {
							ItemStack added = soldier.getHeldItem(EnumHand.MAIN_HAND).copy();
							added.setCount(soldier.getHeldItem(EnumHand.MAIN_HAND).getCount()
									+ chestEntity.getStackInSlot(freeSlot).getCount());
							chestEntity.setInventorySlotContents(freeSlot, added);
						}
						actionDone = true;
					}
				}
			}
		}
	}

	private int findFirstEmptySlot(TileEntityChest chest, Item item) {
		for (int i = 0; i < 27; i++) {
			if ((chest.getStackInSlot(i) == ItemStack.EMPTY)
					|| ((chest.getStackInSlot(i).getItem() == item) && (chest.getStackInSlot(i).getCount() != chest
							.getStackInSlot(i).getItem().getItemStackLimit(chest.getStackInSlot(i))))) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void resetTask() {
		soldier.wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);
	}

}
