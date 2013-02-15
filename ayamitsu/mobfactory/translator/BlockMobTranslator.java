package ayamitsu.mobfactory.translator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// TODO:
public class BlockMobTranslator extends Block implements IEntityStepHandlerBlock {

	public BlockMobTranslator(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public BlockMobTranslator(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void doStepHandler(Entity entity, World world, int x, int y, int z) {
		if (!(entity instanceof EntityLiving) || entity.posY - (double)entity.yOffset - 0.001D > (double)y + this.getBlockBoundsMaxY()) {
			return;
		}

		ItemStack itemStack = MobTranslateUtils.translateToItem((EntityLiving)entity);

		if (itemStack != null) {
			if (!world.isRemote) {
				entity.entityDropItem(itemStack, 0.0F);
				entity.setDead();
			}
		}
	}

}
