package ayamitsu.mobfactory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;

public final class Reflector {

	private static boolean obfuscated;

	public static boolean isObfuscated() {
		return obfuscated;
	}

	public static Method getMethod(Class clazz, String str, Class ... arrayOfClass) throws RuntimeException {
		try {
			Method method = clazz.getDeclaredMethod(str, arrayOfClass);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Constructor getConstructor(Class clazz, Class ... arrayOfClass) throws RuntimeException {
		try {
			Constructor constructor = clazz.getConstructor(arrayOfClass);
			constructor.setAccessible(true);
			return constructor;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object newInstance(Class clazz, Object ... arrayOfObject) throws RuntimeException {
		try {
			Deque<Class> deque = new ArrayDeque<Class>();

			for (int i = 0; i < arrayOfObject.length; i++) {
				deque.add(arrayOfObject[i] != null ? arrayOfObject[i].getClass() : null);
			}

			Constructor constructor = getConstructor(clazz, deque.toArray(new Class[0]));
			return constructor.newInstance(arrayOfObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getPrivateValue(Class clazz, Object instance, int index) throws RuntimeException {
		try {
			Field field = clazz.getDeclaredFields()[index];
			field.setAccessible(true);
			return field.get(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getPrivateValue(Class clazz, Object instance, String str) throws RuntimeException {
		try {
			Field field = clazz.getDeclaredField(str);
			field.setAccessible(true);
			return field.get(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static {
		obfuscated = !net.minecraft.world.World.class.getSimpleName().equals("World");
	}
}
