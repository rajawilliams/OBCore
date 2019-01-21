package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class EntityAIExtractFurnace extends EntityAIBase {

	private World world;
	private EntitySandCivillian soldier;
	private Item itemToFind;
	private float speedIn;

	public EntityAIExtractFurnace(@Nonnull EntitySandCivillian entity, World world, Item item, float speedIn) {
		this.world = world;
		this.soldier = entity;
		this.itemToFind = item;
		this.speedIn = speedIn;
	}

	@Override
	public boolean shouldExecute() {
		boolean output = false;
		if (soldier.hasResidence()) {
			if (soldier.getFurnace() != null) {
				if (soldier.getIndustrialage() > 1) {
					if ((soldier.getHeldItem(EnumHand.MAIN_HAND).isEmpty())
							|| (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == itemToFind)) {
						if (soldier.getFurnace().getStackInSlot(2).getItem() == itemToFind) {
							soldier.wantsToGoTo = soldier.getFurnace().getPos();
							output = true;
						}
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
			if (distance < 4) {
				TileEntityFurnace furnace = soldier.getFurnace();
				IItemHandler handler = furnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
						EnumFacing.DOWN);
				if (soldier.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
					soldier.setHeldItem(EnumHand.MAIN_HAND, handler.extractItem(2, 64, false));
				} else if (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == itemToFind) {
					ItemStack found = handler.extractItem(2, 64, false);
					soldier.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(found.getItem(),
							found.getCount() + soldier.getHeldItem(EnumHand.MAIN_HAND).getCount()));
				}

			}
		}
	}

}
