package ayamitsu.mobfactory.translator;

import java.lang.reflect.Constructor;

import ayamitsu.mobfactory.Loader;
import ayamitsu.mobfactory.MobFactory;
import ayamitsu.mobfactory.util.Reflector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public final class MobTranslateUtils {

	public static ItemStack translateToItem(EntityLiving living) {
		String name = EntityList.getEntityString(living);

		if (name != null) {
			ItemStack itemStack = new ItemStack(Loader.instance.itemMob.itemID, 1, 0);
			itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setString("MobName", name);
			return itemStack;
		}

		return null;
	}

	public static ItemStack translateToItemWithNBT(EntityLiving living) {
		String name = EntityList.getEntityString(living);

		if (name != null) {
			ItemStack itemStack = new ItemStack(Loader.instance.itemMob.itemID, 1, 0);
			itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setString("MobName", name);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			living.writeToNBT(nbttagcompound);
			itemStack.getTagCompound().setCompoundTag("MobNBT", nbttagcompound);
			return itemStack;
		}

		return null;
	}

	public static EntityLiving translateToMob(ItemStack itemStack, World world) {
		String name = itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("MobName") ? itemStack.getTagCompound().getString("MobName") : null;

		if (name != null) {
			Entity entity = EntityList.createEntityByName(name, world);

			if (entity instanceof EntityLiving) {
				return (EntityLiving)entity;
			}
		}

		return null;
	}

	public static EntityLiving translateToMobWithNBT(ItemStack itemStack, World world) {
		if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("MobNBT")) {
			NBTTagCompound nbttagcompound = itemStack.getTagCompound().getCompoundTag("MobNBT");
			Entity entity = EntityList.createEntityFromNBT(nbttagcompound, world);

			if (entity instanceof EntityLiving) {
				return (EntityLiving)entity;
			}
		}

		return null;
	}
}
