package com.obcletter.industryexpansion.entity.ai;

import javax.annotation.Nonnull;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.entity.EntitySandCivillian;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class EntityAIBuildHouse extends EntityAIBase {
	private World world;
	private EntitySandCivillian soldier;

	public EntityAIBuildHouse(@Nonnull EntitySandCivillian entity, World world) {
		this.world = world;
		this.soldier = entity;
	}

	@Override
	public boolean shouldExecute() {
		return (((soldier.getHeldItem(EnumHand.MAIN_HAND).getCount() >= 15)
				&& (soldier.getHeldItem(EnumHand.MAIN_HAND).getItem() == Item.getItemFromBlock(Blocks.LOG)))
				&& !soldier.hasResidence()) && soldier.getIndustrialage() == 1;
	}

	@Override
	public void updateTask() {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftServer = world.getMinecraftServer();
		TemplateManager templateManager = worldserver.getStructureTemplateManager();
		Template template = templateManager.getTemplate(minecraftServer,
				new ResourceLocation(Reference.MOD_ID + ":house"));
		if (template != null) {
			IBlockState iBlockState = world.getBlockState(soldier.getPosition().add(1, 0, 1));
			world.notifyBlockUpdate(soldier.getPosition().add(1, 0, 1), iBlockState, iBlockState, 3);

			PlacementSettings placementSettings = new PlacementSettings().setMirror(Mirror.NONE)
					.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null)
					.setIgnoreStructureBlock(false);
			template.addBlocksToWorld(world, soldier.getPosition().add(1, 0, 1), placementSettings);
			soldier.getHeldItem(EnumHand.MAIN_HAND).splitStack(15);
			soldier.setResidence(true);
			soldier.setHomePosAndDistance(soldier.getPosition().add(3, 0, 3), 512);
			soldier.setCustomNameTag("Resident");
			soldier.setIndustrialage(2);
			soldier.setMaxCooldown(30);
		} else {
			return;
		}
	}

}
