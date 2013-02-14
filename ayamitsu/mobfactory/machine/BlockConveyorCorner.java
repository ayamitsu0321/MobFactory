package ayamitsu.mobfactory.machine;

import java.util.List;

import ayamitsu.mobfactory.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockConveyorCorner extends Block implements IConveyor {

	public BlockConveyorCorner(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public BlockConveyorCorner(int par1, Material par3Material) {
		super(par1, par3Material);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int getRenderType() {
		return Loader.instance.renderConveyorId;
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
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int blockX, int blockY, int blockZ) {
		switch (this.getConvayorStats(blockAccess, blockX, blockY, blockZ)) {
			case NORTH_TO_EAST: ;
			case EAST_TO_NORTH: {
				this.setBlockBounds(0.0625F, 0.0F, 0.0F, 1.0F, 0.375F, 0.9375F);
			} break;
			case EAST_TO_SOUTH: ;
			case SOUTH_TO_EAST: {
				this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.375F, 1.0F);
			} break;
			case SOUTH_TO_WEST: ;
			case WEST_TO_SOUTH: {
				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.375F, 1.0F);
			} break;
			case WEST_TO_NORTH: ;
			case NORTH_TO_WEST: {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.9375F, 0.375F, 0.9375F);
			} break;
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
	}

	@Override
	public ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z) {
		switch (blockAccess.getBlockMetadata(x, y, z) & 7) {
			case 0: return ConveyorStats.NORTH_TO_EAST;
			case 1: return ConveyorStats.EAST_TO_SOUTH;
			case 2: return ConveyorStats.SOUTH_TO_WEST;
			case 3: return ConveyorStats.WEST_TO_NORTH;
			case 4: return ConveyorStats.NORTH_TO_WEST;
			case 5: return ConveyorStats.EAST_TO_NORTH;
			case 6: return ConveyorStats.SOUTH_TO_EAST;
			default: return ConveyorStats.WEST_TO_SOUTH;
		}
	}

	// is right direction
	protected boolean isReverseStats(IBlockAccess blockAccess, int x, int y, int z) {
		return (blockAccess.getBlockMetadata(x, y, z) & 7) > 3;
	}

	@Override
	public boolean isActive(IBlockAccess blockAccess, int x, int y, int z) {
		return true;//blockAccess.getBlockMetadata(x, y, z) > 7;
	}

	@Override
	public boolean canAddVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		if (!entity.onGround || !this.isActive(world, x, y, z)) {
			return false;
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
		double boundingBoxMinY = entity.posY - (double)entity.yOffset;
		return boundingBoxMinY - 0.001D < ((double)y + this.getBlockBoundsMaxY());
	}

	@Override
	public void addVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		double varX = Math.abs(entity.posX - (double)x);
		double varZ = Math.abs(entity.posZ - (double)z);

		switch (this.getConvayorStats(world, x, y, z)) {
			case NORTH_TO_EAST : {
				if ((1.0D - varZ) > varX) {
					if (entity.motionZ > 0.21875D) {
						break;
					}

					entity.motionZ += 0.03125F;

					if (entity.motionZ > 0.21875D) {
						entity.motionZ = 0.21875D;
					}
				} else {
					if (entity.motionX > 0.21875D) {
						break;
					}

					entity.motionX += 0.03125F;

					if (entity.motionX > 0.21875D) {
						entity.motionX = 0.21875D;
					}
				}
			} break;
			case EAST_TO_SOUTH: {
				if (varZ >= varX) {
					if (entity.motionZ > 0.21875D) {
						break;
					}

					entity.motionZ += 0.03125F;

					if (entity.motionZ > 0.21875D) {
						entity.motionZ = 0.21875D;
					}
				} else {
					if (entity.motionX < -0.21875D) {
						break;
					}

					entity.motionX -= 0.03125F;

					if (entity.motionX < -0.21875D) {
						entity.motionX = -0.21875D;
					}
				}
			} break;
			case SOUTH_TO_WEST: {
				if ((1.0D - varZ) < varX) {
					if (entity.motionZ < -0.21875D) {
						break;
					}

					entity.motionZ -= 0.03125F;

					if (entity.motionZ < -0.21875D) {
						entity.motionZ = -0.21875D;
					}
				} else {
					if (entity.motionX < -0.21875D) {
						break;
					}

					entity.motionX -= 0.03125F;

					if (entity.motionX < -0.21875D) {
						entity.motionX = -0.21875D;
					}
				}
			} break;
			case WEST_TO_NORTH: {
				if (varZ <= varX) {
					if (entity.motionZ < -0.21875D) {
						break;
					}

					entity.motionZ -= 0.03125F;

					if (entity.motionZ < -0.21875D) {
						entity.motionZ = -0.21875D;
					}
				} else {
					if (entity.motionX > 0.21875D) {
						break;
					}

					entity.motionX += 0.03125F;

					if (entity.motionX > 0.21875D) {
						entity.motionX = 0.21875D;
					}
				}
			} break;
			case NORTH_TO_WEST: {
				if (varZ < varX) {
					if (entity.motionZ > 0.21875D) {
						break;
					}

					entity.motionZ += 0.03125F;

					if (entity.motionZ > 0.21875D) {
						entity.motionZ = 0.21875D;
					}
				} else {
					if (entity.motionX < -0.21875D) {
						break;
					}

					entity.motionX -= 0.03125F;

					if (entity.motionX < -0.21875D) {
						entity.motionX = -0.21875D;
					}
				}
			} break;
			case WEST_TO_SOUTH: {
				if ((1.0D - varZ) <= varX) {
					if (entity.motionZ > 0.21875D) {
						break;
					}

					entity.motionZ += 0.03125F;

					if (entity.motionZ > 0.21875D) {
						entity.motionZ = 0.21875D;
					}
				} else {
					if (entity.motionX > 0.21875D) {
						break;
					}

					entity.motionX += 0.03125F;

					if (entity.motionX > 0.21875D) {
						entity.motionX = 0.21875D;
					}
				}
			} break;
			case SOUTH_TO_EAST: {
				if (varZ > varX) {
					if (entity.motionZ < -0.21875D) {
						break;
					}

					entity.motionZ -= 0.03125F;

					if (entity.motionZ < -0.21875D) {
						entity.motionZ = -0.21875D;
					}
				} else {
					if (entity.motionX > 0.21875D) {
						break;
					}

					entity.motionX += 0.03125F;

					if (entity.motionX > 0.21875D) {
						entity.motionX = 0.21875D;
					}
				}
			} break;
			case EAST_TO_NORTH: {
				if ((1.0D - varZ) >= varX) {
					if (entity.motionZ < -0.21875D) {
						break;
					}

					entity.motionZ -= 0.03125F;

					if (entity.motionZ < -0.21875D) {
						entity.motionZ = -0.21875D;
					}
				} else {
					if (entity.motionX < -0.21875D) {
						break;
					}

					entity.motionX -= 0.03125F;

					if (entity.motionX < -0.21875D) {
						entity.motionX = -0.21875D;
					}
				}
			} break;
		}
	}

	// not used
	private void addMoveToEntity(Entity entity, boolean isX, double speed, double max) {
		// x
		if (isX) {
			if ((max > 0.0D ? entity.motionX > max : entity.motionX < max)) {
				entity.motionX = speed;
				return;
			}

			entity.motionX += speed;

			if ((max > 0.0D ? entity.motionX > max : entity.motionX < max)) {
				entity.motionX = speed;
				return;
			}
		} else {// z
			if ((max > 0.0D ? entity.motionZ > max : entity.motionZ < max)) {
				entity.motionZ = max;
				return;
			}

			entity.motionZ += speed;

			if ((max > 0.0D ? entity.motionZ > max : entity.motionZ < max)) {
				entity.motionZ = max;
				return;
			}
		}
	}
}
