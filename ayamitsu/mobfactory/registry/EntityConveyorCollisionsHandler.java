package ayamitsu.mobfactory.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import ayamitsu.mobfactory.machine.IConveyor;
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
		/*if (entity instanceof EntityPlayer) {
			System.out.println(MathHelper.floor_double(entity.posY - (double)entity.yOffset - 0.001D));
		}*/

		int minX = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
		int minY = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
		int minZ = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
		int maxX = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
		int maxY = MathHelper.floor_double(entity.posY - entity.yOffset - 0.001D);
		int maxZ = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

		int blockX = MathHelper.floor_double(entity.posX);
		int blockY = MathHelper.floor_double(entity.posY - (double)entity.yOffset - 0.001D);
		int blockZ = MathHelper.floor_double(entity.posZ);
		int blockId = entity.worldObj.getBlockId(blockX, blockY, blockZ);
		Block block;
		IConveyor conveyor;

		if (!entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
			return;
		}

		if (blockId > 0) {
			block = Block.blocksList[blockId];

			if (block instanceof IConveyor) {
				conveyor = (IConveyor)block;

				if (conveyor.canAddVelocityToEntity(entity.worldObj, blockX, blockY, blockZ, entity.posY - (double)entity.yOffset, entity.onGround)) {
					conveyor.addVelocityToEntity(entity, entity.worldObj, blockX, blockY, blockZ);
					return;
				}
			}
		}

		blockId = entity.worldObj.getBlockId(blockX, blockY - 1, blockZ);

		if (blockId > 0) {
			block = Block.blocksList[blockId];

			if (block instanceof IConveyor) {
				conveyor = (IConveyor)block;

				if (conveyor.canAddVelocityToEntity(entity.worldObj, blockX, blockY - 1, blockZ, entity.posY - (double)entity.yOffset, entity.onGround)) {
					conveyor.addVelocityToEntity(entity, entity.worldObj, blockX, blockY - 1, blockZ);
					return;
				}
			}
		}

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				if (x != blockX || z != blockZ) {
					blockId = entity.worldObj.getBlockId(x, blockY, z);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (block instanceof IConveyor) {
							conveyor = (IConveyor)block;

							if (conveyor.canAddVelocityToEntity(entity.worldObj, x, blockY, z, entity.posY - (double)entity.yOffset, entity.onGround)) {
								conveyor.addVelocityToEntity(entity, entity.worldObj, x, blockY, z);
								return;
							}
						}
					}

					blockId = entity.worldObj.getBlockId(x, blockY - 1, z);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (block instanceof IConveyor) {
							conveyor = (IConveyor)block;

							if (conveyor.canAddVelocityToEntity(entity.worldObj, x, blockY - 1, z, entity.posY - (double)entity.yOffset, entity.onGround)) {
								conveyor.addVelocityToEntity(entity, entity.worldObj, x, blockY - 1, z);
								return;
							}
						}
					}
				}
			}
		}
	}
}
