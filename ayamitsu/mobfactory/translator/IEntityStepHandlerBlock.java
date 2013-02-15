package ayamitsu.mobfactory.translator;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IEntityStepHandlerBlock {

	void doStepHandler(Entity entity, World world, int x, int y, int z);

}
