package ayamitsu.mobfactory.item;

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

		String name = living.getEntityName();

		if (name != null) {
			itemStack.setItemDamage(itemStack.getItemDamage() + 1);//itemStack.damageItem(1, living);

			if (!living.worldObj.isRemote) {
				ItemStack mobItem = new ItemStack(Loader.instance.itemMob.itemID, 1, 0);
				mobItem.setTagCompound(new NBTTagCompound());
				mobItem.getTagCompound().setString("MobName", name);
				living.entityDropItem(mobItem, 0.0F);
			}
		}

		return true;
	}
}
