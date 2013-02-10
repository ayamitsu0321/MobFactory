package ayamitsu.mobfactory.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import ayamitsu.mobfactory.registry.MobRenderingRegistry;

public class ItemMob extends Item {

	public ItemMob(int id) {
		super(id);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedItemTooltips) {
		if (!itemStack.hasTagCompound()) {
			return;
		}

		String mobName = itemStack.getTagCompound().getString("MobName");

		if (mobName != null) {
			list.add(StatCollector.translateToLocal(mobName));
		}
	}

	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) {
		ItemStack itemStack;

		for (String name : MobRenderingRegistry.getNames()) {
			itemStack = new ItemStack(id, 1, 0);
			itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setString("MobName", name);
			list.add(itemStack);
		}

	}
}
