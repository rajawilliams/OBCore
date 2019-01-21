package com.obcletter.industryexpansion.entity;

import com.obcletter.industryexpansion.IndustryExpansion;
import com.obcletter.industryexpansion.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

	public static void init() {
		int id = 1;
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "sandcivillian"),
				EntitySandCivillian.class, "sandcivillian", id++, IndustryExpansion.instance, 64, 3, true, 11903624,
				11112567);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "sandsoldier"), EntitySandSoldier.class,
				"sandsoldier", id++, IndustryExpansion.instance, 64, 3, true, 14665645, 14206864);
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
	}
}