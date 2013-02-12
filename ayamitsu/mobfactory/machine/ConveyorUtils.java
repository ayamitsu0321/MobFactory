package ayamitsu.mobfactory.machine;

import static ayamitsu.mobfactory.machine.ConveyorStats.*;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public final class ConveyorUtils {

	public static boolean isConnectedDirection(ConveyorStats conBack, ConveyorStats conFront) {
		switch (conBack) {
			case WEST_TO_SOUTH: ;
			case NORTH_TO_SOUTH: ;
			case EAST_TO_SOUTH: return conFront == SOUTH_TO_WEST || conFront == SOUTH_TO_NORTH || conFront == SOUTH_TO_EAST;
			case SOUTH_TO_WEST: ;
			case NORTH_TO_WEST: ;
			case EAST_TO_WEST: return conFront == WEST_TO_SOUTH || conFront == WEST_TO_NORTH || conFront == WEST_TO_EAST;
			case SOUTH_TO_NORTH: ;
			case WEST_TO_NORTH: ;
			case EAST_TO_NORTH: return conFront == NORTH_TO_SOUTH || conFront == NORTH_TO_WEST || conFront == NORTH_TO_EAST;
			case SOUTH_TO_EAST: ;
			case WEST_TO_EAST: ;
			case NORTH_TO_EAST: return conFront == EAST_TO_SOUTH || conFront == EAST_TO_WEST || conFront == EAST_TO_NORTH;
		}

		throw new IllegalStateException("Unknown Conveyor Stats: back=" + conBack + " front=" + conFront);
	}

	public static boolean isConnectedAndSameDirection(IBlockAccess blockAccess, int backX, int backY, int backZ, int frontX, int frontY, int frontZ) {
		/*if (backX == frontX && backY == frontY && backZ == frontZ) {
			return false;
		}*/

		int difY = frontY - backY;

		// back
		int blockIdBack = blockAccess.getBlockId(backX, backY, backZ);
		Block blockBack;
		IConveyor conveyorBack;
		ConveyorStats statsBack;

		if (blockIdBack <= 0 || !(Block.blocksList[blockIdBack] instanceof IConveyor)) {
			return false;
		}

		blockBack = Block.blocksList[blockIdBack];
		conveyorBack = (IConveyor)blockBack;
		statsBack = conveyorBack.getConvayorStats(blockAccess, backX, backY, backZ);

		// front
		int blockIdFront = blockAccess.getBlockId(frontX, frontY, frontZ);
		Block blockFront;
		IConveyor conveyorFront;
		ConveyorStats statsFront;

		if (blockIdFront <= 0 || !(Block.blocksList[blockIdFront] instanceof IConveyor)) {
			return false;
		}

		blockFront = Block.blocksList[blockIdFront];
		conveyorFront = (IConveyor)blockFront;
		statsFront = conveyorFront.getConvayorStats(blockAccess, frontX, frontY, frontZ);

		boolean connectedDirection = ConveyorUtils.isConnectedDirection(statsBack, statsFront);
		IConveyorSlope conveyorSlopeBack;
		IConveyorSlope conveyorSlopeFront;

		if (conveyorBack instanceof IConveyorSlope) {
			conveyorSlopeBack = (IConveyorSlope)conveyorBack;

			if (conveyorFront instanceof IConveyorSlope) {// both are slope
				conveyorSlopeFront = (IConveyorSlope)conveyorFront;

				if (difY == 1) {
					return connectedDirection && conveyorSlopeBack.isUpStats(blockAccess, backX, backY, backZ) && conveyorSlopeFront.isUpStats(blockAccess, frontX, frontY, frontZ);
				} else if (difY == 0) {
					return connectedDirection && (conveyorSlopeBack.isUpStats(blockAccess, backX, backY, backZ) != conveyorSlopeFront.isUpStats(blockAccess, frontX, frontY, frontZ));
				} else if (difY == -1) {
					return connectedDirection && !conveyorSlopeBack.isUpStats(blockAccess, backX, backY, backZ) && !conveyorSlopeFront.isUpStats(blockAccess, frontX, frontY, frontZ);
				}
			} else {// back is slope
				if (difY == 1) {
					return connectedDirection && conveyorSlopeBack.isUpStats(blockAccess, backX, backY, backZ);
				} else if (difY == 0) {
					return connectedDirection && !conveyorSlopeBack.isUpStats(blockAccess, backX, backY, backZ);
				}
			}
		} else if (conveyorFront instanceof IConveyorSlope) {// front is slope
			conveyorSlopeFront = (IConveyorSlope)conveyorFront;

			if (difY == 0) {
				return connectedDirection && conveyorSlopeFront.isUpStats(blockAccess, frontX, frontY, frontZ);
			}
		} else {// both aren't slope
			return difY == 0 && connectedDirection;
		}

		// or other
		return difY == 0 && connectedDirection;
	}
}
