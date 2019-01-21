package com.obcletter.industryexpansion.client.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRedMeter extends TileEntity {

	private int count;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("count", count);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		count = compound.getInteger("count");
		super.readFromNBT(compound);
	}

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		if (count < 15) {
			count++;
			markDirty();
		}
	}

	public void decrementCount() {
		if (count > 0) {
			count--;
		}
	}
}
