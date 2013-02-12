package ayamitsu.mobfactory.machine;

import net.minecraft.world.IBlockAccess;

public interface IConveyorSlope extends IConveyor {

	boolean isUpStats(IBlockAccess blockAccess, int x, int y, int z);

}
