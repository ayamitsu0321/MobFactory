package ayamitsu.mobfactory.conveyor;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ayamitsu.mobfactory.Loader;

public class BlockConveyorSlope extends Block implements IConveyorSlope {

	protected float velocityValue = 0.03125F;

	public BlockConveyorSlope(int par1, Material par2Material) {
		super(par1, par2Material);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public BlockConveyorSlope(int par1, int par2, Material par3Material) {
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
	public int onBlockPlaced(World world, int blockX, int blockY, int blockZ, int face, float hitX, float hitY, float hitZ, int meta) {
		return face != 0 && (face == 1 || (double)hitY <= 0.5D) ? 4 : 0;
	}

	@Override
	public void onBlockPlacedBy(World world, int blockX, int blockY, int blockZ, EntityLiving living) {
		int yaw = this.getDirectionFromEntityLiving(living);
		boolean isUp = this.isUpStats(world, blockX, blockY, blockZ);
		boolean active = this.isActive(world, blockX, blockY, blockZ);//world.getBlockMetadata(blockX, blockY, blockZ) > 3;
		world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.blockID, (isUp ? yaw : (yaw + 2) & 3) + (isUp ? 4 : 0) + (active ? 8 : 0));
	}

	public int getDirectionFromEntityLiving(EntityLiving living) {
		return MathHelper.floor_double((double)((living.rotationYaw * 4F) / 360F) + 0.5D) & 3;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int blockX, int blockY, int blockZ) {
		switch (this.getConvayorStats(blockAccess, blockX, blockY, blockZ)) {
			case NORTH_TO_SOUTH: ;
			case SOUTH_TO_NORTH: this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 1.375F, 1.0F); return;
			case WEST_TO_EAST: ;
			case EAST_TO_WEST: this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 1.375F, 0.9375F); return;
		}

		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 1.375F, 1.0F);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
	}

	@Override
	public void addCollidingBlockToList(World world, int blockX, int blockY, int blockZ, AxisAlignedBB aabb, List list, Entity entity) {
		int meta = world.getBlockMetadata(blockX, blockY, blockZ);
		boolean up = this.isUpStats(world, blockX, blockY, blockZ);
		// divide limit
		int limit = 20;

		switch (this.getConvayorStats(world, blockX, blockY, blockZ)) {
			case NORTH_TO_SOUTH: {
				if (up) {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0625F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										(1.0F / limit * i) - (1.0F / limit),
										0.9375F,
										0.375F + (float)(1.0F / limit * i),
										1.0F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				} else {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0625F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0F,
										0.9375F,
										0.375F + (float)(1.0F / limit * i),
										1.0F - (1.0F / limit * i) - (1.0F / limit));
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				}

				this.setBlockBounds(0.0625F, 0.0F, 1.0F, 0.9375F, 1.375F, 1.0F);
			} break;
			case EAST_TO_WEST: {
				if (up) {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0625F,
										1.0F - (1.0F / limit * i) - (1.0F / limit),
										0.375F + (float)(1.0F / limit * i),
										0.9375F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				} else {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds((1.0F / limit * i) - (1.0F / limit),
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0625F,
										1.0F,
										0.375F + (float)(1.0F / limit * i),
										0.9375F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				}

				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 1.375F, 0.9275F);
			} break;
			case SOUTH_TO_NORTH: {
				if (up) {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0625F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0F,
										0.9375F,
										0.375F + (float)(1.0F / limit * i),
										1.0F - (1.0F / limit * i) - (1.0F / limit));
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				} else {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0625F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										(1.0F / limit * i) - (1.0F / limit),
										0.9375F,
										0.375F + (float)(1.0F / limit * i),
										1.0F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				}

				this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 1.375F, 1.0F);
			} break;
			case WEST_TO_EAST: {
				if (up) {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds((1.0F / limit * i) - (1.0F / limit),
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0625F,
										1.0F,
										0.375F + (float)(1.0F / limit * i),
										0.9375F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				} else {
					for (int i = 0;i < limit; i++) {
						this.setBlockBounds(0.0F,
								i == 0 ? 0.0F : 0.375F + (float)(1.0F / limit * (i - 1)),
										0.0625F,
										1.0F - (1.0F / limit * i) - (1.0F / limit),
										0.375F + (float)(1.0F / limit * i),
										0.9375F);
						super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
					}
				}

				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 1.375F, 0.9375F);
			} break;
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
	}

	@Override
	public ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);

		switch (meta & 3) {
			case 0: return ConveyorStats.NORTH_TO_SOUTH;
			case 1: return ConveyorStats.EAST_TO_WEST;
			case 2: return ConveyorStats.SOUTH_TO_NORTH;
			case 3: return ConveyorStats.WEST_TO_EAST;
			case 4: return ConveyorStats.SOUTH_TO_NORTH;
			case 5: return ConveyorStats.WEST_TO_EAST;
			case 6: return ConveyorStats.NORTH_TO_SOUTH;
			default: return ConveyorStats.EAST_TO_WEST;
		}
	}

	@Override
	public boolean isActive(IBlockAccess blockAccess, int x, int y, int z) {
		return true;//blockAccess.getBlockMetadata(x, y, z) > 7;
	}

	@Override
	public Vec3 addVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		//System.out.println("slope:" + x + ", " + y + ", " + z);
		Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
		ConveyorStats stats = this.getConvayorStats(world, x, y, z);

		switch (stats) {
			case NORTH_TO_SOUTH: {
				vec3.zCoord += this.velocityValue;
			} break;
			case EAST_TO_WEST: {
				vec3.xCoord -= this.velocityValue;
			} break;
			case SOUTH_TO_NORTH: {
				vec3.zCoord -= this.velocityValue;
			} break;
			case WEST_TO_EAST: {
				vec3.xCoord += this.velocityValue;
			} break;
		}

		return vec3;
	}

	@Override
	public boolean isUpStats(IBlockAccess blockAccess, int x, int y, int z) {
		return (blockAccess.getBlockMetadata(x, y, z) & 7) > 3;
	}

	@Override
	public boolean canAddVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		if (!this.isActive(world, x, y, z)) {
			return false;
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
		double boundingBoxMinY = entity.posY - (double)entity.yOffset;
		return boundingBoxMinY - 0.001D < ((double)y + this.getBlockBoundsMaxY());
	}

}
