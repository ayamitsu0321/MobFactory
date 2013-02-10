package ayamitsu.mobfactory.registry.mobrenderer;

import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import ayamitsu.mobfactory.MobFactory;

public class MobRendererMagmaCube extends MobRendererSlime {

	@Override
	public boolean match(String name) {
		return name.equals("MagmaCube");
	}

	@Override
	public EntitySlime createSlimeInstance() {
		return new EntityMagmaCube(MobFactory.proxy.getClientWorld());
	}
}
