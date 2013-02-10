package ayamitsu.mobfactory.machine;

import net.minecraft.world.IBlockAccess;

public interface IConveyor {

	ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z);

	boolean isActive(IBlockAccess blockAccess, int x, int y, int z);

}
