package ayamitsu.mobfactory.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
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
	public ConveyorStats getConvayorStats(IBlockAccess blockAccess, int x, int y, int z) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean isActive(IBlockAccess blockAccess, int x, int y, int z) {
		return true;//blockAccess.getBlockMetadata(x, y, z) > 7;
	}

	@Override
	public boolean canAddVelocityToEntity(World world, int x, int y, int z, double boundingBoxMinY, boolean onGround) {
		if (onGround || this.isActive(world, x, y, z)) {
			return false;
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
		return boundingBoxMinY - 0.001D > ((double)y - this.getBlockBoundsMaxY());
	}

	@Override
	public void addVelocityToEntity(Entity entity, World world, int x, int y, int z) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
