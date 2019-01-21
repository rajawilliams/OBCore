package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EntityAIFindBlock extends EntityAIBase {

	private World world;
	private Block stateToFind;
	private EntitySandCivillian soldier;
	private float speedIn;
	private boolean usesResidence;
	private int requiredAge;

	private int cooldown = 0;

	private int maximumBlocks;
	private int blocks;

	public EntityAIFindBlock(@Nonnull EntitySandCivillian entity, World world, Block stateToFind, float speedIn,
			boolean usesResidence, int age, int maximumBlocks) {
		this.stateToFind = stateToFind;
		this.world = world;
		this.soldier = entity;
		this.speedIn = speedIn;
		this.usesResidence = usesResidence;
		this.requiredAge = age;
		this.maximumBlocks = maximumBlocks;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		BlockPos position = soldier.getPosition();
		BlockPos pos1 = position.subtract(new Vec3i(10, 2, 10));
		BlockPos pos2 = position.add(new Vec3i(10, 2, 10));
		int max = 100000;
		for (BlockPos pos3 : BlockPos.getAllInBox(pos1, pos2)) {
			if ((soldier.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
					|| (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == Item.getItemFromBlock(stateToFind))) {
				if (requiredAge == soldier.getIndustrialage()) {
					if (pos3.getDistance(position.getX(), position.getY(), position.getZ()) < max) {
						if (world.getBlockState(pos3).getBlock().equals(stateToFind)) {
							if (usesResidence) {
								if (soldier.hasResidence()) {
									max = (int) pos3.getDistance(position.getX(), position.getY(), position.getZ());
									soldier.wantsToGoTo = pos3;
									output = true;
									// cooldown = 0;
								}
							} else {
								if (!soldier.hasResidence()) {
									max = (int) pos3.getDistance(position.getX(), position.getY(), position.getZ());
									soldier.wantsToGoTo = pos3;
									output = true;
									// cooldown = 0;
								}
							}
						}
					}
				}
			}
		}
		return output;
	}

	@Override
	public void updateTask() {
		BlockPos wTGT2 = soldier.wantsToGoTo;

		System.out.println(wTGT2.getX() + " " + wTGT2.getY() + " " + wTGT2.getZ());
		if (wTGT2 != BlockPos.NULL_VECTOR) {
			if (world.getBlockState(soldier.wantsToGoTo).getBlock() == stateToFind) {
				System.out.println(
						soldier.getNavigator().tryMoveToXYZ(wTGT2.getX(), wTGT2.getY(), wTGT2.getZ(), speedIn));
				double distance = soldier.getPosition().getDistance(soldier.wantsToGoTo.getX(),
						soldier.wantsToGoTo.getY(), soldier.wantsToGoTo.getZ());
				if (blocks < maximumBlocks) {
					if ((distance < 4) && (cooldown == 0)) {
						world.destroyBlock(soldier.wantsToGoTo, true);
						blocks++;
						cooldown = soldier.getMaxCooldown();
					}
				}
				// soldier.wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);
			}
		}
		if (cooldown > 0) {
			cooldown--;
		}
	}

	@Override
	public void resetTask() {
		soldier.wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);
	}

}
