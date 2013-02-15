package ayamitsu.mobfactory.registry;

import java.util.ArrayDeque;
import java.util.Deque;

import ayamitsu.mobfactory.conveyor.EntityConveyorCollisionsHandler;
import ayamitsu.mobfactory.translator.EntityStepHandler;

import net.minecraft.entity.Entity;

public class EntityHooks {

	private static final Deque<IEntityHandler> handlerList = new ArrayDeque<EntityHooks.IEntityHandler>();

	public static void registerEntityHandler(IEntityHandler handler) {
		handlerList.add(handler);
	}

	/**
	 * call on the method "doBlockCollisions" in Entity class
	 * insert by asm
	 */
	public static void doBlockCollisions(Entity entity) {
		for (IEntityHandler handler : handlerList) {
			handler.doHandler(entity, HandlerType.DO_BLOCK_COLLISIONS);
		}
	}

	public static interface IEntityHandler {
		void doHandler(Entity entity, HandlerType type);
	}

	public static enum HandlerType {
		DO_BLOCK_COLLISIONS
	}

	static {
		registerEntityHandler(new EntityConveyorCollisionsHandler());
		registerEntityHandler(new EntityStepHandler());
	}
}
