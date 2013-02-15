package ayamitsu.mobfactory.registry.mobrenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.registry.MobRenderingRegistry.IMobRenderer;

public class MobRendererGhast implements IMobRenderer {

	protected EntityGhast ghast;

	@Override
	public boolean match(String name) {
		return name.equals("Ghast");
	}

	@Override
	public void render(String name, ItemRenderType type) {
		if (this.ghast == null) {
			this.ghast = this.createGhastInstance();
		}

		if (this.ghast == null) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.ghast.yOffset + 1.0F, 0.0F);
		GL11.glScalef(0.25F, 0.25F, 0.25F);
		RenderManager.instance.renderEntityWithPosYaw(this.ghast, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected EntityGhast createGhastInstance() {
		return new EntityGhast(MobFactory.proxy.getClientWorld());
	}
}
