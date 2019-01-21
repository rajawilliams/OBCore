package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EntityAISetNullifedResouces extends EntityAIBase {

	private World world;
	private EntitySandCivillian soldier;

	private boolean furnaced = true;
	private boolean chestd = true;

	public EntityAISetNullifedResouces(@Nonnull EntitySandCivillian entity, World world) {
		this.world = world;
		this.soldier = entity;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		if (soldier.hasResidence()) {
			if (soldier.getFurnace() == null) {
				output = true;
				furnaced = false;
			}
			if (soldier.getResourceChest() == null) {
				output = true;
				chestd = false;
			}
		}
		return output;
	}

	@Override
	public void updateTask() {
		BlockPos position = soldier.getPosition();
		BlockPos pos1 = position.subtract(new Vec3i(20, 2, 20));
		BlockPos pos2 = position.add(new Vec3i(20, 2, 20));
		boolean furnaceFound = furnaced;
		boolean chestFound = chestd;
		for (BlockPos pos3 : BlockPos.getAllInBox(pos1, pos2)) {
			if (world.getBlockState(pos3).getBlock() == Blocks.CHEST) {
				TileEntity te = world.getTileEntity(pos3);
				if (te != null) {
					if (!chestFound) {
						if (te instanceof TileEntityChest) {
							TileEntityChest chest = (TileEntityChest) te;
							soldier.setResourceChest(chest);
						}
					}
				}
			}
			if (world.getBlockState(pos3).getBlock() == Blocks.FURNACE) {
				TileEntity te = world.getTileEntity(pos3);
				if (te != null) {
					if (!furnaceFound) {
						if (te instanceof TileEntityFurnace) {
							TileEntityFurnace furnace = (TileEntityFurnace) te;
							soldier.setFurnace(furnace);
						}
					}
				}
			}
		}
	}

}
