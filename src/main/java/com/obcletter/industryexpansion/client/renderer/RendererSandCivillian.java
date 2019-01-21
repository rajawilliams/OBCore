package com.obcletter.industryexpansion.client.renderer;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.entity.EntitySandCivillian;
import com.obcletter.industryexpansion.model.ModelSandBody;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RendererSandCivillian extends RenderBiped<EntitySandCivillian> {

	protected ResourceLocation entityTexture = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/entities/sand.png");

	public static final Factory FACTORY = new Factory();

	public RendererSandCivillian(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSandBody(), 0.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySandCivillian entity) {
		return entityTexture;
	}

	public static class Factory implements IRenderFactory<EntitySandCivillian> {

		@Override
		public Render<? super EntitySandCivillian> createRenderFor(RenderManager manager) {
			return new RendererSandCivillian(manager);
		}

	}

	@Override
	protected void renderLivingAt(EntitySandCivillian entityLivingBaseIn, double x, double y, double z) {
		super.renderLivingAt(entityLivingBaseIn, x, y, z);
		GlStateManager.scale(0.3F, 0.3F, 0.3F);
	}

}
