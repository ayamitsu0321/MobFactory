package ayamitsu.mobfactory.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class AbstractInventory implements IInventory {

	protected ItemStack[] items;

	@Override
	public int getSizeInventory() {
		return this.items.length;
	}

	@Override
	public ItemStack getStackInSlot(int location) {
		return this.items[location];
	}

	@Override
	public ItemStack decrStackSize(int location, int stackSize) {
		if (this.items[location] != null) {
			ItemStack itemStack;

			if (this.items[location].stackSize <= stackSize) {
				itemStack = this.items[location];
				this.items[location] = null;
				return itemStack;
			} else {
				itemStack = this.items[location].splitStack(stackSize);

				if (this.items[location].stackSize == 0) {
					this.items[location] = null;
				}

				return itemStack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int location) {
		if (this.items[location] != null) {
			ItemStack itemStack = this.items[location];
			this.items[location] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int location, ItemStack itemStack) {
		this.items[location] = itemStack;
	}

	@Override
	public abstract String getInvName();

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {}

	@Override
	public abstract boolean isUseableByPlayer(EntityPlayer var1);

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

}
