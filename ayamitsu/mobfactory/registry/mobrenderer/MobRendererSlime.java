package ayamitsu.mobfactory.registry.mobrenderer;

import java.lang.reflect.Method;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.registry.MobRenderingRegistry.IMobRenderer;
import ayamitsu.mobfactory.util.Reflector;

public class MobRendererSlime implements IMobRenderer {

	protected EntitySlime slime;

	@Override
	public boolean match(String name) {
		return name.equals("Slime");
	}

	@Override
	public void render(String name, ItemRenderType type) {
		if (!(this.slime instanceof EntitySlime)) {
			this.slime = this.createSlimeInstance();
			this.setSlimeSize(this.slime, 2);// fixation
		}

		if (this.slime == null) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.slime.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.slime, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public EntitySlime createSlimeInstance() {
		return new EntitySlime(MobFactory.proxy.getClientWorld());
	}

	@SuppressWarnings("unchecked")// 1.4.6
	private void setSlimeSize(EntitySlime entitySlime, int i) {
		try {
			Method method = Reflector.getMethod(net.minecraft.entity.monster.EntitySlime.class, Reflector.isObfuscated() ? "a" : "setSlimeSize", Integer.TYPE);
			method.invoke(entitySlime, new Object[] { i });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
