package ayamitsu.mobfactory.machine.translator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import ayamitsu.mobfactory.machine.conveyor.IConveyor;
import ayamitsu.mobfactory.registry.EntityHooks.HandlerType;
import ayamitsu.mobfactory.registry.EntityHooks.IEntityHandler;

public class EntityStepHandler implements IEntityHandler {

	@Override
	public void doHandler(Entity entity, HandlerType type) {
		if (type == HandlerType.DO_BLOCK_COLLISIONS) {
			this.doStepCollisions(entity);
		}
	}

	private void doStepCollisions(Entity entity) {
		int minX = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
		int minY = MathHelper.floor_double(entity.posY - (double)entity.yOffset + 0.001D - 1D);
		int minZ = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
		int maxX = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
		int maxY = MathHelper.floor_double(entity.posY - (double)entity.yOffset - 0.001D);
		int maxZ = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

		int blockId;// = entity.worldObj.getBlockId(blockX, blockY, blockZ);
		Block block;

		if (!entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
			return;
		}

		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = minY; y <= maxY; y++) {
					blockId = entity.worldObj.getBlockId(x, y, z);

					if (blockId > 0) {
						block = Block.blocksList[blockId];

						if (block instanceof IEntityStepHandlerBlock) {
							((IEntityStepHandlerBlock)block).doStepHandler(entity, entity.worldObj, x, y, z);
						}
					}
				}
			}
		}
	}

}
