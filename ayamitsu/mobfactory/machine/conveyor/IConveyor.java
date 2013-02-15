package ayamitsu.mobfactory.machine.conveyor;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
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
	 */
	boolean canAddVelocityToEntity(Entity entity, World world, int x, int y, int z);

	/**
	 * if canAddVelocityToEntity return true, call this
	 */
	Vec3 addVelocityToEntity(Entity entity, World world, int x, int y, int z);

}
