package ayamitsu.mobfactory.registry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererMagmaCube;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererNormal;
import ayamitsu.mobfactory.registry.mobrenderer.MobRendererSlime;

public class MobRenderingRegistry {

	private static Deque<IMobRenderer> rendererList = new ArrayDeque<IMobRenderer>();
	private static Set<String> nameList = new HashSet<String>();
	private static Map<String, String> localizeMapping = new HashMap<String, String>();

	/**
	 * name use on ItemMob#getSubItems
	 */
	public static void registerRenderer(String name, IMobRenderer renderer) {
		nameList.add(name);
		rendererList.add(renderer);
	}

	public static void addLocalization(String original, String name) {
		localizeMapping.put(original, name);
	}

	/**
	 * localize name for make ItemMob
	 * and use on get renderer from name
	 * example: LavaSlime -> MagmaCube
	 */
	public static String localize(String origin) {
		String name = localizeMapping.get(origin);

		if (name == null) {
			name = origin;
		}

		return name;
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

		return null;
	}

	/**
	 * use on ItemMob#getSubItems
	 */
	public static String[] getNames() {
		return nameList.toArray(new String[0]);
	}

	static {
		registerRenderer("Zombie", new MobRendererNormal("Zombie", EntityZombie.class));
		registerRenderer("Skeleton", new MobRendererNormal("Skeleton", EntitySkeleton.class));
		registerRenderer("Creeper", new MobRendererNormal("Creeper", EntityCreeper.class));
		registerRenderer("Spider", new MobRendererNormal("Spider", EntitySpider.class));
		registerRenderer("Enderman", new MobRendererNormal("Enderman", EntityEnderman.class));
		registerRenderer("PigZombie", new MobRendererNormal("PigZombie", EntityPigZombie.class));
		registerRenderer("Cow", new MobRendererNormal("Cow", EntityCow.class));
		registerRenderer("Pig", new MobRendererNormal("Pig", EntityPig.class));
		registerRenderer("Sheep", new MobRendererNormal("Sheep", EntitySheep.class));
		registerRenderer("Chicken", new MobRendererNormal("Chicken", EntityChicken.class));
		registerRenderer("IronGolem", new MobRendererNormal("IronGolem", EntityIronGolem.class));
		registerRenderer("Slime", new MobRendererSlime());
		registerRenderer("MagmaCube", new MobRendererMagmaCube());
		addLocalization("VillagerGolem", "IronGolem");
		addLocalization("LavaSlime", "MagmaCube");
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
}
