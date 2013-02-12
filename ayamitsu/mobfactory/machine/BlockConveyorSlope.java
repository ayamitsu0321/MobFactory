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

public class BlockConveyorSlope extends Block implements IConveyorSlope {

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
	public void onBlockPlacedBy(World world, int blockX, int blockY, int blockZ, EntityLiving living) {
		int yaw = this.getDirectionFromEntityLiving(living);
		boolean active = world.getBlockMetadata(blockX, blockY, blockZ) > 3;
		world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, this.blockID, yaw + (active ? 4 : 0));

		if (!world.isRemote) {
			System.out.println(yaw);
		}
	}

	public int getDirectionFromEntityLiving(EntityLiving living) {
		return MathHelper.floor_double((double)((living.rotationYaw * 4F) / 360F) + 0.5D) & 3;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int blockX, int blockY, int blockZ) {
		switch (this.getConvayorStats(blockAccess, blockX, blockY, blockZ)) {
			case NORTH_TO_SOUTH: ;
			case SOUTH_TO_NORTH: this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 1.0F, 1.0F); return;
			case WEST_TO_EAST: ;
			case EAST_TO_WEST: this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 1.0F, 0.9375F); return;
		}

		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 1.0F, 1.0F);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
	}

	@Override
	public void addCollidingBlockToList(World world, int blockX, int blockY, int blockZ, AxisAlignedBB aabb, List list, Entity entity) {
		int meta = world.getBlockMetadata(blockX, blockY, blockZ);
		boolean up = (meta & 7) > 3;
		// divided 8
		switch (this.getConvayorStats(world, blockX, blockY, blockZ)) {
			case NORTH_TO_SOUTH: {
				int limit = 10;

				for (int i = 0;i < limit; i++) {
					this.setBlockBounds(0.0625F, 0.375F + (float)(1.0F / limit * (i - 1)), (1.0F / limit * i) - (1.0F / limit), 0.9375F, 0.375F + (float)(1.0F / limit * i), 1.0F);
					super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				}

				/*this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.375F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);

				this.setBlockBounds(0.0625F, 0.375F, 0.125F, 0.9375F, 0.475F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.5F, 0.25F, 0.9375F, 0.575F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.625F, 0.375F, 0.9375F, 0.675F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.75F, 0.5F, 0.9375F, 0.775F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.875F, 0.625F, 0.9375F, 0.875F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.9F, 0.75F, 0.9375F, 0.975F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 1.025F, 0.875F, 0.9375F, 1.075F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 1.025F, 1.075F, 0.9375F, 1.275F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);

				this.setBlockBounds(0.0625F, 1.0F, 1.275F, 0.9375F, 1.375F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);*/
				/*this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.125F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.125F, 0.125F, 0.9375F, 0.25F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.25F, 0.25F, 0.9375F, 0.375F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.375F, 0.375F, 0.9375F, 0.5F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.5F, 0.5F, 0.9375F, 0.625F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.625F, 0.625F, 0.9375F, 0.75F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.75F, 0.75F, 0.9375F, 0.875F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
				this.setBlockBounds(0.0625F, 0.875F, 0.875F, 0.9375F, 1.0F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);*/
			} break;
			case EAST_TO_WEST: {
				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 0.125F, 0.9375F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
			} break;
			case SOUTH_TO_NORTH: {
				this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.125F, 1.0F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
			} break;
			case WEST_TO_EAST: {
				this.setBlockBounds(0.0F, 0.0F, 0.0625F, 1.0F, 0.125F, 0.9375F);
				super.addCollidingBlockToList(world, blockX, blockY, blockZ, aabb, list, entity);
			} break;
		}
	}

	/*@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int blockX, int blockY, int blockZ) {
		this.setBlockBoundsBasedOnState(world, blockX, blockY, blockZ);
		return super.getCollisionBoundingBoxFromPool(world, blockX, blockY, blockZ);
		//return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)blockX, (double)blockY, (double)blockZ, (double)blockX + 1.0D, (double)blockY + 0.375D, (double)blockZ + 1.0D);
	}*/

	/*@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int blockX, int blockY, int blockZ) {
		this.setBlockBoundsBasedOnState(world, blockX, blockY, blockZ);
		return super.getSelectedBoundingBoxFromPool(world, blockX, blockY, blockZ);
		//return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)blockX, (double)blockY, (double)blockZ, (double)blockX + 1.0D, (double)blockY + 0.375D, (double)blockZ + 1.0D);
	}*/

	/**
	 * second must be in front of first
	 */
	/*private boolean isConnectedAndSameDirection(IBlockAccess blockAccess, int firstX, int firstY, int firstZ, int secondX, int secondY, int secondZ) {
		if (firstX == secondX && firstY == secondY && firstZ == secondZ) {
			return false;
		}

		int difY = secondY - firstY;

		// first
		int blockIdFirst = blockAccess.getBlockId(firstX, firstY, firstZ);
		Block blockFirst;
		IConveyor conveyorFirst;
		ConveyorStats statsFirst;

		if (blockIdFirst <= 0 || !(Block.blocksList[blockIdFirst] instanceof IConveyor)) {
			return false;
		}

		blockFirst = Block.blocksList[blockIdFirst];
		conveyorFirst = (IConveyor)blockFirst;
		statsFirst = conveyorFirst.getConvayorStats(blockAccess, firstX, firstY, firstZ);

		// second
		int blockIdSecond = blockAccess.getBlockId(secondX, secondY, secondZ);
		Block blockSecond;
		IConveyor conveyorSecond;
		ConveyorStats statsSecond;

		if (blockIdSecond <= 0 || !(Block.blocksList[blockIdSecond] instanceof IConveyor)) {
			return false;
		}

		blockSecond = Block.blocksList[blockIdSecond];
		conveyorSecond = (IConveyor)blockSecond;
		statsSecond = conveyorSecond.getConvayorStats(blockAccess, secondX, secondY, secondZ);

		boolean connectedDirection = ConveyorUtils.isConnectedDirection(statsFirst, statsSecond);
		IConveyorSlope conveyorSlopeFirst;
		IConveyorSlope conveyorSlopeSecond;

		if (conveyorFirst instanceof IConveyorSlope) {
			conveyorSlopeFirst = (IConveyorSlope)conveyorFirst;

			if (conveyorSecond instanceof IConveyorSlope) {// both are slope
				conveyorSlopeSecond = (IConveyorSlope)conveyorSecond;

				if (difY == 1) {
					return connectedDirection && conveyorSlopeFirst.isUpStats(blockAccess, firstX, firstY, firstZ) && conveyorSlopeSecond.isUpStats(blockAccess, secondX, secondY, secondZ);
				} else if (difY == 0) {
					return connectedDirection && (conveyorSlopeFirst.isUpStats(blockAccess, firstX, firstY, firstZ) != conveyorSlopeSecond.isUpStats(blockAccess, secondX, secondY, secondZ));
				} else if (difY == -1) {
					return connectedDirection && !conveyorSlopeFirst.isUpStats(blockAccess, firstX, firstY, firstZ) && !conveyorSlopeSecond.isUpStats(blockAccess, secondX, secondY, secondZ);
				}
			} else {// first is slope
				if (difY == 1) {
					return connectedDirection && conveyorSlopeFirst.isUpStats(blockAccess, firstX, firstY, firstZ);
				} else if (difY == 0) {
					return connectedDirection && !conveyorSlopeFirst.isUpStats(blockAccess, firstX, firstY, firstZ);
				}
			}
		} else if (conveyorSecond instanceof IConveyorSlope) {// second is slope
			conveyorSlopeSecond = (IConveyorSlope)conveyorSecond;

			if (difY == 0) {
				return connectedDirection && conveyorSlopeSecond.isUpStats(blockAccess, secondX, secondY, secondZ);
			}
		}

		// both aren't slope or other
		return difY == 0 && connectedDirection;
	}*/

	private boolean isRequiredAddVelocityToEntity(Entity entity, World world, int x, int y, int z, boolean working) {
		if (x != MathHelper.floor_double(entity.posX) || z != MathHelper.floor_double(entity.posZ)) {
			ConveyorStats stats = this.getConvayorStats(world, x, y, z);
			int x1 = x;
			int y1 = y;
			int z1 = z;

			// front
			switch (stats) {
				case WEST_TO_SOUTH: ;
				case NORTH_TO_SOUTH: ;
				case EAST_TO_SOUTH: z1++; break;
				case SOUTH_TO_WEST: ;
				case NORTH_TO_WEST: ;
				case EAST_TO_WEST: x1--; break;
				case SOUTH_TO_NORTH: ;
				case WEST_TO_NORTH: ;
				case EAST_TO_NORTH: z1--; break;
				case SOUTH_TO_EAST: ;
				case WEST_TO_EAST: ;
				case NORTH_TO_EAST: x1++; break;
			}

			int blockId;
			Block block;
			IConveyor conveyor;
			ConveyorStats stats1;

			if (ConveyorUtils.isConnectedAndSameDirection(world, x, y, z, x1, y1, z1)) {
				blockId = world.getBlockId(x1, y1, z1);

				if (blockId > 0 && Block.blocksList[blockId] instanceof IConveyor) {
					conveyor = (IConveyor)Block.blocksList[blockId];

					if (!working || conveyor.isActive(world, x1, y1, z1)) {
						return false;
					}
				}
			}

			x1 = x;
			y1 = y;
			z1 = z;

			// TODO:

			// back
			switch (stats) {
				case WEST_TO_SOUTH: ;
				case NORTH_TO_SOUTH: ;
				case EAST_TO_SOUTH: z1--; break;
				case SOUTH_TO_WEST: ;
				case NORTH_TO_WEST: ;
				case EAST_TO_WEST: x1++; break;
				case SOUTH_TO_NORTH: ;
				case WEST_TO_NORTH: ;
				case EAST_TO_NORTH: z1++; break;
				case SOUTH_TO_EAST: ;
				case WEST_TO_EAST: ;
				case NORTH_TO_EAST: x1--; break;
			}

			blockId = world.getBlockId(x1, y1, z1);

			if (ConveyorUtils.isConnectedAndSameDirection(world, x1, y1, z1, x, y, z)) {
				blockId = world.getBlockId(x1, y1, z1);

				if (blockId > 0 && Block.blocksList[blockId] instanceof IConveyor) {
					conveyor = (IConveyor)Block.blocksList[blockId];

					if (!working || conveyor.isActive(world, x1, y1, z1)) {
						return false;
					}
				}
			}
		}

		return true;

			/*blockId = world.getBlockId(x1, y1, z1);

			if (blockId > 0) {
				block = Block.blocksList[blockId];

				if (block instanceof IConveyor) {
					conveyor = (IConveyor)block;
					stats1 = conveyor.getConvayorStats(world, x1, y1, z1);
					boolean flag = false;

					if (conveyor instanceof IConveyorSlope) {
						if (((IConveyorSlope)conveyor).isUpStats(world, x1, y1, z1) != isUp) {
							flag = true;
						}
					}

					switch (stats1) {
						case SOUTH_TO_NORTH: ;
						case SOUTH_TO_WEST: ;
						case SOUTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.WEST_TO_SOUTH || stats == ConveyorStats.NORTH_TO_SOUTH || stats == ConveyorStats.EAST_TO_SOUTH)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case WEST_TO_SOUTH: ;
						case WEST_TO_NORTH: ;
						case WEST_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_WEST || stats == ConveyorStats.NORTH_TO_WEST || stats == ConveyorStats.EAST_TO_WEST)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case NORTH_TO_SOUTH: ;
						case NORTH_TO_WEST: ;
						case NORTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_NORTH || stats == ConveyorStats.WEST_TO_NORTH || stats == ConveyorStats.EAST_TO_NORTH)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case EAST_TO_SOUTH: ;
						case EAST_TO_NORTH: ;
						case EAST_TO_WEST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_EAST || stats == ConveyorStats.WEST_TO_EAST || stats == ConveyorStats.NORTH_TO_EAST)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
					}
				}
			}

			blockId = world.getBlockId(x1, y1 + (isUp ? +1 : -1), z1);

			if (blockId > 0) {
				block = Block.blocksList[blockId];

				if (block instanceof IConveyor) {
					conveyor = (IConveyor)block;
					stats1 = conveyor.getConvayorStats(world, x1, y1, z1);
					boolean flag = false;

					if (conveyor instanceof IConveyorSlope) {
						if (((IConveyorSlope)conveyor).isUpStats(world, x1, y1, z1) != isUp) {
							flag = true;
						}
					}

					switch (stats1) {
						case SOUTH_TO_NORTH: ;
						case SOUTH_TO_WEST: ;
						case SOUTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.WEST_TO_SOUTH || stats == ConveyorStats.NORTH_TO_SOUTH || stats == ConveyorStats.EAST_TO_SOUTH)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case WEST_TO_SOUTH: ;
						case WEST_TO_NORTH: ;
						case WEST_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_WEST || stats == ConveyorStats.NORTH_TO_WEST || stats == ConveyorStats.EAST_TO_WEST)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case NORTH_TO_SOUTH: ;
						case NORTH_TO_WEST: ;
						case NORTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_NORTH || stats == ConveyorStats.WEST_TO_NORTH || stats == ConveyorStats.EAST_TO_NORTH)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
						case EAST_TO_SOUTH: ;
						case EAST_TO_NORTH: ;
						case EAST_TO_WEST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_EAST || stats == ConveyorStats.WEST_TO_EAST || stats == ConveyorStats.NORTH_TO_EAST)) {
								if (working && conveyor.isActive(world, x1, y1, z1)) {
									return false;
								}
							}
						} break;
					}
				}
			}

			x1 = x;
			y1 = y;
			z1 = z;

			// TODO:

			// back
			switch (stats) {
				case WEST_TO_SOUTH: ;
				case NORTH_TO_SOUTH: ;
				case EAST_TO_SOUTH: z1--; break;
				case SOUTH_TO_WEST: ;
				case NORTH_TO_WEST: ;
				case EAST_TO_WEST: x1++; break;
				case SOUTH_TO_NORTH: ;
				case WEST_TO_NORTH: ;
				case EAST_TO_NORTH: z1++; break;
				case SOUTH_TO_EAST: ;
				case WEST_TO_EAST: ;
				case NORTH_TO_EAST: x1--; break;
			}

			blockId = world.getBlockId(x1, y1, z1);

			if (blockId > 0) {
				block = Block.blocksList[blockId];

				if (block instanceof IConveyor) {
					conveyor = (IConveyor)block;
					stats1 = conveyor.getConvayorStats(world, x1, y1, z1);
					boolean flag = false;

					if (conveyor instanceof IConveyorSlope) {
						if (((IConveyorSlope)conveyor).isUpStats(world, x1, y1, z1) == isUp) {
							flag = true;
						}
					}

					switch (stats1) {
						case SOUTH_TO_NORTH: ;
						case SOUTH_TO_WEST: ;
						case SOUTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.WEST_TO_SOUTH || stats == ConveyorStats.NORTH_TO_SOUTH || stats == ConveyorStats.EAST_TO_SOUTH)) {
								System.out.println("ss");
								return false;
							}
						} break;
						case WEST_TO_SOUTH: ;
						case WEST_TO_NORTH: ;
						case WEST_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_WEST || stats == ConveyorStats.NORTH_TO_WEST || stats == ConveyorStats.EAST_TO_WEST)) {
								return false;
							}
						} break;
						case NORTH_TO_SOUTH: ;
						case NORTH_TO_WEST: ;
						case NORTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_NORTH || stats == ConveyorStats.WEST_TO_NORTH || stats == ConveyorStats.EAST_TO_NORTH)) {
								return false;
							}
						} break;
						case EAST_TO_SOUTH: ;
						case EAST_TO_NORTH: ;
						case EAST_TO_WEST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_EAST || stats == ConveyorStats.WEST_TO_EAST || stats == ConveyorStats.NORTH_TO_EAST)) {
								return false;
							}
						} break;
					}
				}
			}

			blockId = world.getBlockId(x1, y1 - 1, z1);

			if (blockId > 0) {
				block = Block.blocksList[blockId];

				if (block instanceof IConveyor) {
					conveyor = (IConveyor)block;
					stats1 = conveyor.getConvayorStats(world, x1, y1, z1);
					boolean flag = false;

					if (conveyor instanceof IConveyorSlope) {
						if (((IConveyorSlope)conveyor).isUpStats(world, x1, y1, z1) != isUp) {
							flag = true;
						}
					}

					switch (stats1) {
						case SOUTH_TO_NORTH: ;
						case SOUTH_TO_WEST: ;
						case SOUTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.WEST_TO_SOUTH || stats == ConveyorStats.NORTH_TO_SOUTH || stats == ConveyorStats.EAST_TO_SOUTH)) {
								return false;
							}
						} break;
						case WEST_TO_SOUTH: ;
						case WEST_TO_NORTH: ;
						case WEST_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_WEST || stats == ConveyorStats.NORTH_TO_WEST || stats == ConveyorStats.EAST_TO_WEST)) {
								return false;
							}
						} break;
						case NORTH_TO_SOUTH: ;
						case NORTH_TO_WEST: ;
						case NORTH_TO_EAST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_NORTH || stats == ConveyorStats.WEST_TO_NORTH || stats == ConveyorStats.EAST_TO_NORTH)) {
								return false;
							}
						} break;
						case EAST_TO_SOUTH: ;
						case EAST_TO_NORTH: ;
						case EAST_TO_WEST: {
							if (flag ^ (stats == ConveyorStats.SOUTH_TO_EAST || stats == ConveyorStats.WEST_TO_EAST || stats == ConveyorStats.NORTH_TO_EAST)) {
								return false;
							}
						} break;
					}
				}
			}
		}

		return true;*/

		/*ConveyorStats stats = this.getConvayorStats(world, x, y, z);

		if (x != MathHelper.floor_double(entity.posX) || z != MathHelper.floor_double(entity.posZ)) {
			// see the method of Entity Class, "doBlockCollisions"
			int minX = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
			int minY = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
			int minZ = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
			int maxX = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
			int maxY = MathHelper.floor_double(entity.boundingBox.maxY - 0.001D);
			int maxZ = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

			int x1 = x;
			int y1 = y;
			int z1 = z;

			boolean up = true;//(world.getBlockMetadata(x, y, z) & 7) > 3;

			if (up) {
				y1++;
			}

			// front
			switch (stats) {
				case WEST_TO_SOUTH: ;
				case NORTH_TO_SOUTH: ;
				case EAST_TO_SOUTH: z1++; break;
				case SOUTH_TO_WEST: ;
				case NORTH_TO_WEST: ;
				case EAST_TO_WEST: x1--; break;
				case SOUTH_TO_NORTH: ;
				case WEST_TO_NORTH: ;
				case EAST_TO_NORTH: z1--; break;
				case SOUTH_TO_EAST: ;
				case WEST_TO_EAST: ;
				case NORTH_TO_EAST: x1++; break;
			}

			Block block = Block.blocksList[world.getBlockId(x1, y1, z1)];

			if (block instanceof IConveyor) {
				if (working && ((IConveyor)block).isActive(world, x1, y1, z1)) {
					return false;
				}

				if (entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
					if (x1 >= minX && x1 <= maxX && y1 >= minY && y1 <= maxY && z1 >= minZ && z1 <= maxZ) {
						//System.out.println("inside_front:" + x1 + ", " + y1 + ", " + z1);
						return false;
					}
				}
			}

			x1 = x;
			y1 = y;
			z1 = z;

			if (up) {
				y1++;
			}

			// back
			switch (stats) {
				case WEST_TO_SOUTH: ;
				case NORTH_TO_SOUTH: ;
				case EAST_TO_SOUTH: z1--; break;
				case SOUTH_TO_WEST: ;
				case NORTH_TO_WEST: ;
				case EAST_TO_WEST: x1++; break;
				case SOUTH_TO_NORTH: ;
				case WEST_TO_NORTH: ;
				case EAST_TO_NORTH: z1++; break;
				case SOUTH_TO_EAST: ;
				case WEST_TO_EAST: ;
				case NORTH_TO_EAST: x1--; break;
			}

			block = Block.blocksList[world.getBlockId(x1, y1, z1)];

			if (block instanceof IConveyor) {
				if (working && ((IConveyor)block).isActive(world, x1, y1, z1)) {
					return false;
				}

				if (entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ)) {
					if (x1 >= minX && x1 <= maxX && y1 >= minY && y1 <= maxY && z1 >= minZ && z1 <= maxZ) {
						//System.out.println("inside_back:" + x1 + ", " + y1 + ", " + z1);
						return false;
					}
				}
			}
		}

		return true;*/
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
			default: return ConveyorStats.WEST_TO_EAST;
		}
	}

	@Override
	public boolean isActive(IBlockAccess blockAccess, int x, int y, int z) {
		return true;//blockAccess.getBlockMetadata(x, y, z) > 7;
	}

	@Override
	public void addVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		System.out.println("slope:" + x + ", " + y + ", " + z);
		// TODO:

		/*if (!entity.onGround) {
			return;
		}*/

		// not active state
		if (!this.isActive(world, x, y, z)) {
			return;
		}

		if (!this.isRequiredAddVelocityToEntity(entity, world, x, y, z, false)) {
			return;
		}

		ConveyorStats stats = this.getConvayorStats(world, x, y, z);
		boolean up = this.isUpStats(world, x, y, z);

		switch (stats) {
			case NORTH_TO_SOUTH: {
				if (entity.motionZ <= 0.21875D) {
					entity.motionZ += 0.03125F;

					if (entity.motionZ > 0.21875D) {
						entity.motionZ = 0.21875D;
					}
				}

				if (up && entity.motionY <= 0.0125D) {
					entity.motionY += 0.0125F;

					if (entity.motionY > 0.0125D) {
						entity.motionY = 0.0125D;
					}
				}
			} break;
			case EAST_TO_WEST: {
				if (entity.motionX >= -0.21875D) {
					entity.motionX -= 0.03125F;

					if (entity.motionX < -0.21875D) {
						entity.motionX = -0.21875D;
					}
				}

				if (up && entity.motionY <= 0.0125D) {
					entity.motionY += 0.0125F;

					if (entity.motionY > 0.0125D) {
						entity.motionY = 0.0125D;
					}
				}
			} break;
			case SOUTH_TO_NORTH: {
				if (entity.motionZ >= -0.21875D) {
					entity.motionZ -= 0.03125F;

					if (entity.motionZ < -0.21875D) {
						entity.motionZ = -0.21875D;
					}
				}

				if (up && entity.motionY <= 0.0125D) {
					entity.motionY += 0.0125F;

					if (entity.motionY > 0.0125D) {
						entity.motionY = 0.0125D;
					}
				}
			} break;
			case WEST_TO_EAST: {
				if (entity.motionX <= 0.21875D) {
					entity.motionX += 0.03125F;

					if (entity.motionX > 0.21875D) {
						entity.motionX = 0.21875D;
					}
				}

				if (up && entity.motionY <= 0.0125D) {
					entity.motionY += 0.0125F;

					if (entity.motionY > 0.0125D) {
						entity.motionY = 0.0125D;
					}
				}
			} break;
		}
	}

	@Override
	public boolean isUpStats(IBlockAccess blockAccess, int x, int y, int z) {
		return true;//(blockAccess.getBlockMetadata(x, y, z) & 7) > 3;
	}

}
