package com.obcletter.industryexpansion.client.renderer;

import com.obcletter.industryexpansion.Reference;
import com.obcletter.industryexpansion.entity.EntitySandSoldier;
import com.obcletter.industryexpansion.model.ModelSandBody;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RendererSandSoldier extends RenderBiped<EntitySandSoldier> {

	protected ResourceLocation entityTexture = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/entities/sandsoldier.png");

	public static final Factory FACTORY = new Factory();

	public RendererSandSoldier(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSandBody(), 0.1f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySandSoldier entity) {
		return entityTexture;
	}

	public static class Factory implements IRenderFactory<EntitySandSoldier> {

		@Override
		public Render<? super EntitySandSoldier> createRenderFor(RenderManager manager) {
			return new RendererSandSoldier(manager);
		}

	}

	@Override
	protected void renderLivingAt(EntitySandSoldier entityLivingBaseIn, double x, double y, double z) {
		super.renderLivingAt(entityLivingBaseIn, x, y, z);
		GlStateManager.scale(0.3F, 0.3F, 0.3F);
	}

}
