package ayamitsu.mobfactory.machine;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemConveyorCorner extends ItemBlock {

	public ItemConveyorCorner(int par1) {
		super(par1);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		int yaw = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		boolean isActive = stack.getItemDamage() > 7;
		boolean isLeft = yaw == 0 ? hitX > 0.5D : yaw == 1 ? hitZ > 0.5D : yaw == 2 ? hitX <= 0.5D : hitZ <= 0.5D;
		metadata = yaw + (isLeft ? 0 : 4) + (isActive ? 8 : 0);
		System.out.println("player corner:" + metadata);

		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
	}
}
