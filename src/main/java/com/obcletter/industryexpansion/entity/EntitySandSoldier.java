package com.obcletter.industryexpansion.entity;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntitySandSoldier extends EntityMob {

	public EntitySandSoldier(World worldIn) {
		super(worldIn);
		int id = 0;

		tasks.addTask(id++, new EntityAISwimming(this));
		tasks.addTask(id++, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(id++, new EntityAIAttackMelee(this, 2.0F, true));
		tasks.addTask(id++, new EntityAILookIdle(this));
		tasks.addTask(id++, new EntityAIWanderAvoidWater(this, 1.0D));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityCreeper.class, true));
		this.setSize(0.3F, 0.6F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
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
	public EnumPushReaction getPushReaction() {
		return EnumPushReaction.DESTROY;
	}

}
