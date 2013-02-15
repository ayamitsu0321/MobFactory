package ayamitsu.mobfactory.registry.mobrenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.registry.MobRenderingRegistry.IMobRenderer;

public class MobRendererSquid implements IMobRenderer {

	protected EntitySquid squid;

	@Override
	public boolean match(String name) {
		return name.equals("Squid");
	}

	@Override
	public void render(String name, ItemRenderType type) {
		if (this.squid == null) {
			this.squid = this.createSquidInstance();
		}

		if (this.squid == null) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.squid.yOffset + 1.2F, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.squid, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected EntitySquid createSquidInstance() {
		return new EntitySquid(MobFactory.proxy.getClientWorld());
	}
}
