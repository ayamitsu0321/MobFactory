package ayamitsu.mobfactory.machine;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * implemented by Block
 */
public interface IConveyor {

	ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z);

	boolean isActive(IBlockAccess blockAccess, int x, int y, int z);

	/**
	 * auto call
	 */
	void addVelocityToEntity(Entity entity, World world, int x, int y, int z);

}
