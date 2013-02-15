package ayamitsu.mobfactory.conveyor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import ayamitsu.mobfactory.registry.EntityHooks;
import ayamitsu.mobfactory.registry.EntityHooks.HandlerType;
import ayamitsu.mobfactory.registry.EntityHooks.IEntityHandler;

/**
 * for conveyor
 */
public class EntityConveyorCollisionsHandler implements IEntityHandler {

	@Override
	public void doHandler(Entity entity, HandlerType type) {
		if (type == HandlerType.DO_BLOCK_COLLISIONS) {
			this.doConveyorCollisions(entity);
		}
	}

	// if addVelocity, finish execute
	public static void doConveyorCollisions(Entity entity) {
		int minX = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
		int minY = MathHelper.floor_double(entity.posY - (double)entity.yOffset + 0.001D - 1D);
		int minZ = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
		int maxX = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
		int maxY = MathHelper.floor_double(entity.posY - entity.yOffset - 0.001D);
		int maxZ = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

		int blockId;// = entity.worldObj.getBlockId(blockX, blockY, blockZ);
		Block block;
		IConveyor conveyor;

		if (!entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
			return;
		}

		List<Vec3> list = new ArrayList<Vec3>();

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = minY; y <= maxY; y++) {
					blockId = entity.worldObj.getBlockId(x, y, z);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (block instanceof IConveyor) {
							conveyor = (IConveyor)block;

							if (conveyor.canAddVelocityToEntity(entity, entity.worldObj, x, y, z)) {
								list.add(conveyor.addVelocityToEntity(entity, entity.worldObj, x, y, z));
							}
						}
					}
				}
			}
		}

		if (!list.isEmpty()) {
			Vec3 vec3Base = entity.worldObj.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);

			for (Vec3 vec3 : list) {
				vec3Base = vec3Base.addVector(vec3.xCoord, vec3.yCoord, vec3.zCoord);
			}

			vec3Base.xCoord *= 1.0D / (double)list.size();
			vec3Base.yCoord *= 1.0D / (double)list.size();
			vec3Base.zCoord *= 1.0D / (double)list.size();
			entity.motionX += vec3Base.xCoord;
			entity.motionY += vec3Base.yCoord;
			entity.motionZ += vec3Base.zCoord;
		}
	}

}
