package ayamitsu.mobfactory.machine.conveyor;

/**
 * South:0 +z
 * West: 1 -x
 * North:2 -z
 * East: 3 +x
 */
public enum ConveyorStats {
	NORTH_TO_WEST,
	NORTH_TO_SOUTH,
	NORTH_TO_EAST,
	WEST_TO_NORTH,
	WEST_TO_SOUTH,
	WEST_TO_EAST,
	SOUTH_TO_NORTH,
	SOUTH_TO_WEST,
	SOUTH_TO_EAST,
	EAST_TO_NORTH,
	EAST_TO_WEST,
	EAST_TO_SOUTH;
}
