package ayamitsu.mobfactory.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import ayamitsu.mobfactory.machine.IConveyor;
import ayamitsu.mobfactory.registry.EntityHooks.HandlerType;
import ayamitsu.mobfactory.registry.EntityHooks.IEntityHandler;

public class EntityConveyorCollisionsHandler implements IEntityHandler {

	@Override
	public void doHandler(Entity entity, HandlerType type) {
		if (type == HandlerType.DO_BLOCK_COLLISIONS) {
			this.doConveyorCollisions(entity);
		}
	}

	public static void doConveyorCollisions(Entity entity) {
		if (entity.isSneaking()) {
			//return;
		}

		int minX = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
		int minY = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
		int minZ = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
		int maxX = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
		int maxY = MathHelper.floor_double(entity.posY - entity.yOffset - 0.001D);
		int maxZ = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

		int blockX;
		int blockY = MathHelper.floor_double(entity.posY - entity.yOffset);
		int blockZ;

		double varY = entity.posY - entity.yOffset - 0.001D;//entity.boundingBox.minY - 0.001D;

		int blockId;
		Block block;

		if (entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
			for (blockX = minX; blockX <= maxX; blockX++) {
				for (blockZ = minZ; blockZ <= maxZ; blockZ++) {
					blockId = entity.worldObj.getBlockId(blockX, blockY, blockZ);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (blockY + block.getBlockBoundsMaxY() > varY) {
							if (block instanceof IConveyor) {
								((IConveyor)block).addVelocityToEntity(entity, entity.worldObj, blockX, blockY, blockZ);
								continue;
							}
						}
					}

					blockId = entity.worldObj.getBlockId(blockX, blockY - 1, blockZ);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (blockY + block.getBlockBoundsMaxY() > varY) {
							if (block instanceof IConveyor) {
								((IConveyor)block).addVelocityToEntity(entity, entity.worldObj, blockX, blockY, blockZ);
								continue;
							}
						}
					}
				}
			}
		}
	}
}
