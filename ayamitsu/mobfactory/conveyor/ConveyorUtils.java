package ayamitsu.mobfactory.conveyor;

import static ayamitsu.mobfactory.conveyor.ConveyorStats.*;

public final class ConveyorUtils {

	public static boolean isConnectedDirection(ConveyorStats conBack, ConveyorStats conFront) {
		switch (conBack) {
			case WEST_TO_SOUTH: ;
			case NORTH_TO_SOUTH: ;
			case EAST_TO_SOUTH: return conFront == NORTH_TO_SOUTH || conFront == NORTH_TO_WEST || conFront == NORTH_TO_EAST;
			case SOUTH_TO_WEST: ;
			case NORTH_TO_WEST: ;
			case EAST_TO_WEST: return conFront == EAST_TO_SOUTH || conFront == EAST_TO_WEST || conFront == EAST_TO_NORTH;
			case SOUTH_TO_NORTH: ;
			case WEST_TO_NORTH: ;
			case EAST_TO_NORTH: return conFront == SOUTH_TO_WEST || conFront == SOUTH_TO_NORTH || conFront == SOUTH_TO_EAST;
			case SOUTH_TO_EAST: ;
			case WEST_TO_EAST: ;
			case NORTH_TO_EAST: return conFront == WEST_TO_SOUTH || conFront == WEST_TO_NORTH || conFront == WEST_TO_EAST;
		}

		throw new IllegalStateException("Unknown Conveyor Stats: back=" + conBack + " front=" + conFront);
	}

}
