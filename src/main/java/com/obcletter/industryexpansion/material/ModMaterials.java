package com.obcletter.industryexpansion.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class ModMaterials extends Material {

	public ModMaterials(MapColor color) {
		super(color);
	}

	public static final Material MACHINE = new ModMaterials(MapColor.IRON).setImmovableMobility();
}
