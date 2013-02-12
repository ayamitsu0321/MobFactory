package ayamitsu.mobfactory.machine;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * implemented by Block
 */
public interface IConveyor {

	/**
	 * return direction
	 */
	ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z);

	boolean isActive(IBlockAccess blockAccess, int x, int y, int z);

	/**
	 * if return true, call addVelocityToEntity
	 * boundingBoxMinY and onGround is entity's stats
	 */
	boolean canAddVelocityToEntity(World world, int x, int y, int z, double boundingBoxMinY, boolean onGround);

	/**
	 * if canAddVelocityToEntity return true, call this
	 */
	void addVelocityToEntity(Entity entity, World world, int x, int y, int z);

}
