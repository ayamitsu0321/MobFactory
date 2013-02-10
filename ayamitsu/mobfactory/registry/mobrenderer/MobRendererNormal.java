package ayamitsu.mobfactory.registry.mobrenderer;

import java.lang.reflect.Constructor;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.registry.MobRenderingRegistry.IMobRenderer;
import ayamitsu.mobfactory.util.Reflector;

public class MobRendererNormal implements IMobRenderer {

	private Entity entity;
	private String name;
	private Class entityClass;

	public MobRendererNormal(String str, Class clazz) {
		this.name = str;
		this.entityClass = clazz;
	}

	@Override
	public boolean match(String name) {
		return this.name.equals(name);
	}

	@Override
	public void render(String name, ItemRenderType type) {
		if (this.entity == null) {
			try {
				Constructor constructor = Reflector.getConstructor(this.entityClass, World.class);
				this.entity = (Entity)constructor.newInstance(MobFactory.proxy.getClientWorld());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (this.entity == null) {
			return;
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, this.entity.yOffset, 0.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
