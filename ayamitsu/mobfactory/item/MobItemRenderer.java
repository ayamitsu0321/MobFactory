package ayamitsu.mobfactory.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import ayamitsu.mobfactory.registry.MobRenderingRegistry;
import ayamitsu.mobfactory.registry.MobRenderingRegistry.IMobRenderer;

/**
 * IItemRenderer
 *   ItemRenderType
 *     ENTITY
 *     EQUIPPED
 *     INVENTORY
 *     FIRST_PERSON_MAP
 *   ItemRendererHelper
 *     ENTITY_ROTATION
 *     ENTITY_BOBBING
 *     EQUIPPED_BLOCK
 *     BLOCK_3D
 *     INVENTORY_BLOCK
 */

public class MobItemRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		if (!(itemStack.getItem() instanceof ItemMob) || type == ItemRenderType.INVENTORY) {
			return false;
		}

		/*switch (type) {
			case ENTITY: ; break;
			case EQUIPPED: ; break;
			case INVENTORY: return false;
			case FIRST_PERSON_MAP: ; break;
		}*/

		return itemStack.hasTagCompound() && MobRenderingRegistry.contains(itemStack.getTagCompound().getString("MobName"));
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemStack, ItemRendererHelper helper) {
		/*switch (helper) {
			case ENTITY_ROTATION: ; break;
			case ENTITY_BOBBING: ; break;
			case EQUIPPED_BLOCK: ; break;
			case BLOCK_3D: ; break;
			case INVENTORY_BLOCK: ; break;
		}*/

		if (type == ItemRenderType.ENTITY) {
			switch (helper) {
				case ENTITY_ROTATION: return true;
				case ENTITY_BOBBING: return true;
			}
		} else if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.FIRST_PERSON_MAP) {
			return true;
		} else if (type == ItemRenderType.INVENTORY) {
			if (helper == ItemRendererHelper.INVENTORY_BLOCK) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		Entity entity = null;
		String entityName = itemStack.getTagCompound().getString("MobName");

		IMobRenderer renderer = MobRenderingRegistry.getRenderer(entityName, type);

		if (renderer != null) {
			GL11.glPushMatrix();

			/*if (type == ItemRenderType.ENTITY) {
				;
			}*/
			if (type == ItemRenderType.EQUIPPED) {
				//GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				//GL11.glTranslatef(-0.8125F, 0.0F, 0.0F);
				//GL11.glTranslatef(0.0F, 0.0F, 0.25F);

				GL11.glRotatef(-180F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.75F);
			}
			if (type == ItemRenderType.FIRST_PERSON_MAP) {
				//.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			}
			/*if (type == ItemRenderType.INVENTORY) {
				;
			}*/

			renderer.render(entityName, type);
			GL11.glPopMatrix();
		}

		/*switch (type) {
			case ENTITY: RenderHelper.enableStandardItemLighting(); break;
			case EQUIPPED: RenderHelper.enableStandardItemLighting(); break;
			//case INVENTORY: ; break;
			case FIRST_PERSON_MAP: RenderHelper.enableStandardItemLighting(); break;
		}*/

	}
}
