package ayamitsu.mobfactory;

import net.minecraft.world.World;

public class CommonProxy {

	public void preLoad() {
	}

	public void load() {
	}

	public boolean isClientSide() {
		return false;
	}

	public World getClientWorld() {
		return null;
	}

	public int getUniqueBlockModelId() {
		return -1;
	}
}
