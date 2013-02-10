package ayamitsu.mobfactory.machine;

import ayamitsu.mobfactory.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMobConveyorSlope extends Block implements IConveyor {

	public BlockMobConveyorSlope(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public BlockMobConveyorSlope(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int getRenderType() {
		return Loader.instance.renderConveyorSlopeId;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		int top = 147;
		int left = 0;
		int right = 45;
		int front = 46;
		int back = 62;
		int bottom = 131;

		if (side == 0) {
			return bottom;
		} else if (side == 1) {
			return top;
		}

		if (side - 2 == (meta & 3)) {
			return front;
		} else if (side - 2 == ((meta + 1) & 3)) {
			return right;
		} else if (side - 2 == ((meta + 2) & 3)) {
			return back;
		} else if (side - 2 == ((meta + 3) & 3)) {
			return left;
		}

		return super.getBlockTextureFromSideAndMetadata(side, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int blockX, int blockY, int blockZ, EntityLiving living) {
		int yaw = this.getDirectionFromEntityLiving(living);
		boolean active = world.getBlockMetadata(blockX, blockY, blockZ) > 3;
		world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.blockID, yaw + (active ? 4 : 0));

		if (!world.isRemote) {
			System.out.println(yaw);
		}
	}

	/**
	 * return 0 ~ 3
	 */
	public int getMoveDirection(IBlockAccess blockAccess, int x, int y, int z) {
		ConveyorStats stats = this.getConvayorStats(blockAccess, x, y, z);

		switch (stats) {
			case NORTH_TO_SOUTH: return 0;
			case EAST_TO_WEST: return 1;
			case SOUTH_TO_NORTH: return 2;
			default: return 3;// WEST_TO_SOUTH
		}
	}

	public int getDirectionFromEntityLiving(EntityLiving living) {
		return MathHelper.floor_double((double)((living.rotationYaw * 4F) / 360F) + 0.5D) & 3;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int blockX, int blockY, int blockZ) {
		switch (this.getConvayorStats(blockAccess, blockX, blockY, blockZ)) {
			case NORTH_TO_SOUTH: ;
			case SOUTH_TO_NORTH: this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F); return;
			case WEST_TO_EAST: ;
			case EAST_TO_WEST: this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 0.375F, 0.9375F); return;
		}

		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int blockX, int blockY, int blockZ) {
		this.setBlockBoundsBasedOnState(world, blockX, blockY, blockZ);
		return super.getCollisionBoundingBoxFromPool(world, blockX, blockY, blockZ);
		//return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)blockX, (double)blockY, (double)blockZ, (double)blockX + 1.0D, (double)blockY + 0.375D, (double)blockZ + 1.0D);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int blockX, int blockY, int blockZ) {
		this.setBlockBoundsBasedOnState(world, blockX, blockY, blockZ);
		return super.getSelectedBoundingBoxFromPool(world, blockX, blockY, blockZ);
		//return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)blockX, (double)blockY, (double)blockZ, (double)blockX + 1.0D, (double)blockY + 0.375D, (double)blockZ + 1.0D);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int blockX, int blockY, int blockZ, Entity entity) {
		if (!entity.onGround) {
			return;
		}

		// not active state
		/*if (meta < 4) {
			return;
		}*/

		if (blockX != MathHelper.floor_double(entity.posX) || blockZ != MathHelper.floor_double(entity.posZ)) {
			return;
		}

		int direction = getMoveDirection(world, blockX, blockY, blockZ);

		switch (direction) {
			case 0: {
				entity.motionZ -= 0.03125F;

				if (entity.motionZ < -0.21875D) {
					entity.motionZ = -0.21875D;
				}
			} break;
			case 1: {
				entity.motionX += 0.03125F;

				if (entity.motionX > 0.21875D) {
					entity.motionX = 0.21875D;
				}
			} break;
			case 2: {
				entity.motionZ += 0.03125F;

				if (entity.motionZ > 0.21875D) {
					entity.motionZ = 0.21875D;
				}
			} break;
			case 3: {
				entity.motionX -= 0.03125F;

				if (entity.motionX < -0.21875D) {
					entity.motionX = -0.21875D;
				}
			} break;
		}
	}

	@Override
	public ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z) {
		switch (blockAccess.getBlockMetadata(x, y, z) & 3) {
			case 0: return ConveyorStats.SOUTH_TO_NORTH;
			case 1: return ConveyorStats.WEST_TO_EAST;
			case 2: return ConveyorStats.NORTH_TO_SOUTH;
			default: return ConveyorStats.EAST_TO_WEST;
		}
	}

	@Override
	public boolean isActive(IBlockAccess blockAccess, int x, int y, int z) {
		return blockAccess.getBlockMetadata(x, y, z) > 7;
	}

}
