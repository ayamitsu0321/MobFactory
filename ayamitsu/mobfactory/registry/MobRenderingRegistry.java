package ayamitsu.mobfactory.registry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererGhast;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererMagmaCube;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererNormal;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererSlime;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererSquid;
import ayamitsu.mobfactory.util.Reflector;

public class MobRenderingRegistry {

	private static Deque<IMobRenderer> rendererList = new ArrayDeque<IMobRenderer>();
	private static Set<String> nameList = new HashSet<String>();

	/**
	 * name use on ItemMob#getSubItems
	 */
	public static void registerRenderer(String name, IMobRenderer renderer) {
		nameList.add(name);
		rendererList.add(renderer);
	}

	public static boolean contains(String name) {
		return nameList.contains(name);
	}

	/**
	 * type must not be INVENTORY on MobItemRenderer
	 */
	public static IMobRenderer getRenderer(String name, ItemRenderType type) {
		if (name == null) {
			return null;
		}

		for (IMobRenderer renderer : rendererList) {
			if (renderer.match(name)) {
				return renderer;
			}
		}

		return MobRendererFlexible.singleton;
	}

	/**
	 * use on ItemMob#getSubItems
	 */
	public static String[] getNames() {
		return nameList.toArray(new String[0]);
	}

	/**
	 * implement this and register
	 * use that renderer
	 */
	public static interface IMobRenderer {

		boolean match(String name);

		/** render mob */
		void render(String name, ItemRenderType type);

	}

	private static class MobRendererFlexible implements IMobRenderer {

		public static final MobRendererFlexible singleton = new MobRendererFlexible();
		private static Map<String, Entity> entityRenderingMapping;

		@Override
		public boolean match(String name) {
			return name != null;
		}

		@Override
		public void render(String name, ItemRenderType type) {
			if (entityRenderingMapping == null) {
				entityRenderingMapping = new HashMap<String, Entity>();
			}

			Entity entity = entityRenderingMapping.get(name);

			if (entity == null) {
				Class entityClass = (Class)this.getStringToClassMapping().get(name);

				if (entityClass == null) {
					return;
				}

				try {
					entity = (Entity)Reflector.getConstructor(entityClass, new Class[] { World.class }).newInstance(MobFactory.proxy.getClientWorld());
					entityRenderingMapping.put(name, entity);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}

			if (entity == null) {
				return;
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
			RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		private Map getStringToClassMapping() {
			return (Map)Reflector.getPrivateValue(EntityList.class, null, 0);
		}

	}

	static {
		registerRenderer("Zombie", new MobRendererNormal("Zombie", EntityZombie.class));
		registerRenderer("Skeleton", new MobRendererNormal("Skeleton", EntitySkeleton.class));
		registerRenderer("Creeper", new MobRendererNormal("Creeper", EntityCreeper.class));
		registerRenderer("Spider", new MobRendererNormal("Spider", EntitySpider.class));
		registerRenderer("CaveSpider", new MobRendererNormal("CaveSpider", EntityCaveSpider.class));
		registerRenderer("Enderman", new MobRendererNormal("Enderman", EntityEnderman.class));
		registerRenderer("PigZombie", new MobRendererNormal("PigZombie", EntityPigZombie.class));
		registerRenderer("Blaze", new MobRendererNormal("Blaze", EntityBlaze.class));
		registerRenderer("Silverfish", new MobRendererNormal("Silverfish", EntitySilverfish.class));
		registerRenderer("Cow", new MobRendererNormal("Cow", EntityCow.class));
		registerRenderer("MushroomCow", new MobRendererNormal("MushroomCow", EntityMooshroom.class));
		registerRenderer("Pig", new MobRendererNormal("Pig", EntityPig.class));
		registerRenderer("Sheep", new MobRendererNormal("Sheep", EntitySheep.class));
		registerRenderer("Chicken", new MobRendererNormal("Chicken", EntityChicken.class));
		registerRenderer("Wolf", new MobRendererNormal("Wolf", EntityWolf.class));
		registerRenderer("Ozelot", new MobRendererNormal("Ozelot", EntityOcelot.class));
		registerRenderer("Villager", new MobRendererNormal("Villager", EntityVillager.class));
		registerRenderer("Witch", new MobRendererNormal("Witch", EntityWitch.class));
		registerRenderer("VillagerGolem", new MobRendererNormal("VillagerGolem", EntityIronGolem.class));
		registerRenderer("SnowMan", new MobRendererNormal("SnowMan", EntitySnowman.class));
		registerRenderer("Ghast", new MobRendererGhast());
		registerRenderer("Squid", new MobRendererSquid());
		registerRenderer("Slime", new MobRendererSlime());
		registerRenderer("LavaSlime", new MobRendererMagmaCube());
	}

}
