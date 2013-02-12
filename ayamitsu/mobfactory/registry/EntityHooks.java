package ayamitsu.mobfactory.registry;

import java.util.ArrayDeque;
import java.util.Deque;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class EntityHooks {

	private static final Deque<IEntityHandler> handlerList = new ArrayDeque<EntityHooks.IEntityHandler>();

	public static void registerEntityHandler(IEntityHandler handler) {
		handlerList.add(handler);
	}

	public static void doBlockCollisions(Entity entity) {
		for (IEntityHandler handler : handlerList) {
			handler.doHandler(entity, HandlerType.DO_BLOCK_COLLISIONS);
		}
	}

	public static interface IEntityHandler {
		void doHandler(Entity entity, HandlerType type);
	}

	public enum HandlerType {
		DO_BLOCK_COLLISIONS
	}

	static {
		registerEntityHandler(new EntityConveyorCollisionsHandler());
	}
}
