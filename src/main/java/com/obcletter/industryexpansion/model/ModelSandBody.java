package com.obcletter.industryexpansion.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSandBody extends ModelBiped {

	public ModelSandBody() {
		this(0.0f);
	}

	public ModelSandBody(float scale) {
		super(scale, 0.0F, 64, 64);
		this.bipedHeadwear.isHidden = true;
	}

}
