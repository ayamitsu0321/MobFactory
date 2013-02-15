package ayamitsu.mobfactory.translator;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ayamitsu.mobfactory.Loader;

/**
 * catch mob and add item instance of mob into player inevntory
 */
public class ItemMobTranslator extends Item {

	public ItemMobTranslator(int id) {
		super(id);
		this.setMaxStackSize(1);
		this.setMaxDamage(20);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemStack, EntityLiving living) {
		if (itemStack.getItemDamage() >= this.getMaxDamage()) {
			return false;
		}

		ItemStack mobItem = MobTranslateUtils.translateToItem(living);

		if (mobItem != null) {
			itemStack.setItemDamage(itemStack.getItemDamage() + 1);

			if (!living.worldObj.isRemote) {
				living.setDead();
				living.entityDropItem(mobItem, 0.0F);
			}

			return true;
		}

		return false;
	}
}
