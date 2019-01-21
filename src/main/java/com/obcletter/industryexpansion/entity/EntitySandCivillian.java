package com.obcletter.industryexpansion.entity;

import com.obcletter.industryexpansion.IndustryExpansion;
import com.obcletter.industryexpansion.entity.ai.EntityAIBuildHouse;
import com.obcletter.industryexpansion.entity.ai.EntityAIExtractFurnace;
import com.obcletter.industryexpansion.entity.ai.EntityAIFindBlock;
import com.obcletter.industryexpansion.entity.ai.EntityAIFindItem;
import com.obcletter.industryexpansion.entity.ai.EntityAISetNullifedResouces;
import com.obcletter.industryexpansion.entity.ai.EntityAISmeltItems;
import com.obcletter.industryexpansion.entity.ai.EntityAIStoreInInventory;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySandCivillian extends EntityAnimal {

	public BlockPos wantsToGoTo = new BlockPos(BlockPos.NULL_VECTOR);

	private boolean residence = false;
	private int loveCooldown = 0;
	private int maxLoveCooldown = 1600;

	private int industrialage = 1;

	private int maxCooldown = 40;

	private TileEntityChest resourceChest;
	private TileEntityFurnace furnace;

	private static final float excitedSpeed = 1.25f;
	private static final float normalSpeed = 1.00f;

	public EntitySandCivillian(World worldIn) {
		super(worldIn);
	}

	public boolean isResidence() {
		return residence;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		if (IndustryExpansion.rand.nextInt(5) == 4) {
			new EntitySandSoldier(this.world).setPosition(posX, posY, posZ);

		}
		EntitySandCivillian baby = new EntitySandCivillian(this.world);
		baby.setCustomNameTag("Resident");
		baby.setResidence(true);
		baby.setIndustrialage(this.industrialage);
		return baby;

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.setCustomNameTag("Civillian");
		this.setSize(0.3F, 0.6F);
	}

	@Override
	protected void initEntityAI() {
		World worldIn = this.world;
		int id = 0;
		tasks.addTask(id++, new EntityAISwimming(this));
		tasks.addTask(id++, new EntityAIMate(this, 1.0D));
		tasks.addTask(id++, new EntityAITempt(this, this.excitedSpeed, Item.getItemFromBlock(Blocks.SAND), true));
		tasks.addTask(id++, new EntityAIFollowParent(this, this.excitedSpeed));
		tasks.addTask(id++, new EntityAILookIdle(this));
		tasks.addTask(id++, new EntityAIWanderAvoidWater(this, this.normalSpeed));
		tasks.addTask(id++, new EntityAISetNullifedResouces(this, worldIn));

		tasks.addTask(id++, new EntityAIFindBlock(this, worldIn, Blocks.LOG, this.normalSpeed, false, 1, 15));
		tasks.addTask(id++, new EntityAIFindItem(this, worldIn, Item.getItemFromBlock(Blocks.LOG), this.normalSpeed));
		tasks.addTask(id++, new EntityAIBuildHouse(this, worldIn));
		tasks.addTask(id++,
				new EntityAIStoreInInventory(this, worldIn, this.normalSpeed, Item.getItemFromBlock(Blocks.LOG)));

		tasks.addTask(id++, new EntityAIFindBlock(this, worldIn, Blocks.COAL_ORE, this.excitedSpeed, true, 2, 3));
		tasks.addTask(id++, new EntityAIFindItem(this, worldIn, Items.COAL, this.excitedSpeed));
		tasks.addTask(id++, new EntityAISmeltItems(this, worldIn, this.excitedSpeed, Items.COAL, 1));

		tasks.addTask(id++, new EntityAIFindBlock(this, worldIn, Blocks.IRON_ORE, this.excitedSpeed, true, 2, 3));
		tasks.addTask(id++,
				new EntityAIFindItem(this, worldIn, Item.getItemFromBlock(Blocks.IRON_ORE), this.excitedSpeed));
		tasks.addTask(id++,
				new EntityAISmeltItems(this, worldIn, this.excitedSpeed, Item.getItemFromBlock(Blocks.IRON_ORE), 0));

		tasks.addTask(id++, new EntityAIExtractFurnace(this, worldIn, Items.IRON_INGOT, this.excitedSpeed));

	}

	public int getIndustrialage() {
		return industrialage;
	}

	public void setIndustrialage(int industrialage) {
		this.industrialage = industrialage;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (IndustryExpansion.rand.nextInt(1000) == 999) {
			if (!this.isInLove()) {
				if (!this.isChild()) {
					if (this.residence) {
						if (loveCooldown == 0) {
							this.setInLove(null);
						}
					}
				}
			}
		}
		if (loveCooldown > 0) {
			loveCooldown--;
		}
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_SAND_BREAK;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("residence", residence);
		return super.writeToNBT(compound);
	}

	public boolean hasResidence() {
		return residence;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		residence = compound.getBoolean("residence");
		super.readFromNBT(compound);
	}

	public void setResidence(boolean residence) {
		this.residence = residence;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.SAND);
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	public TileEntityChest getResourceChest() {
		return resourceChest;
	}

	public void setResourceChest(TileEntityChest resourceChest) {
		this.resourceChest = resourceChest;
	}

	public TileEntityFurnace getFurnace() {
		return furnace;
	}

	public void setFurnace(TileEntityFurnace furnace) {
		this.furnace = furnace;
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return true;
	}

	public int getMaxCooldown() {
		return maxCooldown;
	}

	public void setMaxCooldown(int maxCooldown) {
		this.maxCooldown = maxCooldown;
	}

}
