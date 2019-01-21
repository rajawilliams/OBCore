package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EntityAIFindItem extends EntityAIBase {
	private World world;
	private Item itemToFind;
	private EntitySandCivillian soldier;
	private float speedIn;

	private EntityItem foundItem;

	public EntityAIFindItem(@Nonnull EntitySandCivillian entity, World world, Item itemToFind, float speedIn) {
		this.itemToFind = itemToFind;
		this.world = world;
		this.soldier = entity;
		this.speedIn = speedIn;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		int max = 100000;
		for (EntityItem itemEntity : world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
				soldier.getPosition().add(20, 3, 20), soldier.getPosition().subtract(new Vec3i(20, 3, 20))))) {
			// System.out.println(itemEntity);
			if ((soldier.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
					|| (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == itemToFind)) {
				if (itemEntity.getPosition().getDistance(soldier.getPosition().getX(), soldier.getPosition().getY(),
						soldier.getPosition().getZ()) < max) {
					if (itemEntity.getItem().getItem() == itemToFind) {
						max = (int) itemEntity.getPosition().getDistance(soldier.getPosition().getX(),
								soldier.getPosition().getY(), soldier.getPosition().getZ());
						soldier.wantsToGoTo = itemEntity.getPosition();
						foundItem = itemEntity;
						output = true;
					}
				}

			}
		}

		return output;

	}

	@Override
	public void updateTask() {
		if (soldier.wantsToGoTo != BlockPos.NULL_VECTOR) {
			soldier.getNavigator().setPath(soldier.getNavigator().getPathToPos(
					new BlockPos(soldier.wantsToGoTo.getX(), soldier.wantsToGoTo.getY(), soldier.wantsToGoTo.getZ())),
					speedIn);
			double distance = soldier.getPosition().getDistance(soldier.wantsToGoTo.getX(), soldier.wantsToGoTo.getY(),
					soldier.wantsToGoTo.getZ());
			if (distance < 2) {
				if (soldier.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
					soldier.setHeldItem(EnumHand.MAIN_HAND, foundItem.getItem());
					foundItem.setDead();
				} else if (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == foundItem.getItem().getItem()) {
					soldier.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(
							soldier.getHeldItem(EnumHand.MAIN_HAND).getItem(),
							soldier.getHeldItem(EnumHand.MAIN_HAND).getCount() + foundItem.getItem().getCount()));
					foundItem.setDead();
				}
			}
		}

	}

	@Override
	public void resetTask() {
		soldier.wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);
	}

}
