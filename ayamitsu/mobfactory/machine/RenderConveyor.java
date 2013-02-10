package ayamitsu.mobfactory.machine;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderConveyor implements ISimpleBlockRenderingHandler {

	private int renderId;

	public RenderConveyor(int render) {
		this.renderId = render;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderBlocks) {
		GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		block.setBlockBoundsForItemRender();
		renderBlocks.setRenderBoundsFromBlock(block);
		Tessellator var0 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		var0.startDrawingQuads();
		double x = 0.0D;
		double y = 0.0D;
		double z = 0.0D;
		int texture;
		int direction = meta & 3;

		// bottom
		var0.setNormal(0.0F, -1.0F, 0.0F);
		renderBlocks.uvRotateBottom = direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 0 : 2;
		texture = block.getBlockTextureFromSideAndMetadata(0, meta);
		renderBlocks.renderBottomFace(block, x, y, z, texture);
		renderBlocks.uvRotateBottom = 0;

		// top
		var0.setNormal(0.0F, 1.0F, 0.0F);
		renderBlocks.uvRotateTop = direction == 0 ? 3 : direction == 1 ? 2 : direction == 2 ? 0 : 1;
		texture = block.getBlockTextureFromSideAndMetadata(1, meta);
		renderBlocks.renderTopFace(block, x, y, z, texture);
		renderBlocks.uvRotateTop = 0;

		//

		// south
		var0.setNormal(0.0F, 0.0F, -1.0F);
		texture = block.getBlockTextureFromSideAndMetadata(2, meta);
		renderBlocks.renderEastFace(block, x, y, z, texture);

		// west
		var0.setNormal(1.0F, 0.0F, 0.0F);
		texture = block.getBlockTextureFromSideAndMetadata(3, meta);
		renderBlocks.renderSouthFace(block, x, y, z, texture);

		// north
		var0.setNormal(0.0F, 0.0F, 1.0F);
		texture = block.getBlockTextureFromSideAndMetadata(4, meta);
		renderBlocks.renderWestFace(block, x, y, z, texture);

		// east
		var0.setNormal(-1.0F, 0.0F, 0.0F);
		texture = block.getBlockTextureFromSideAndMetadata(5, meta);
		renderBlocks.renderNorthFace(block, x, y, z, texture);

		var0.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks) {
		if (modelId != this.renderId || !(block instanceof IConveyor)) {
			return false;
		}

		IConveyor conveyor = (IConveyor)block;
		Tessellator var0 = Tessellator.instance;
		var0.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));

		int texture;

		int meta = blockAccess.getBlockMetadata(x, y, z);

		var0.setColorOpaque_I(block.getRenderColor(meta));

		int direction = meta & 3;

		// bottom
		renderBlocks.uvRotateBottom = direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 0 : 2;
		texture = block.getBlockTexture(blockAccess, x, y, z, 0);
		renderBlocks.renderBottomFace(block, x, y, z, texture);
		renderBlocks.uvRotateBottom = 0;

		// top
		renderBlocks.uvRotateTop = direction == 0 ? 3 : direction == 1 ? 2 : direction == 2 ? 0 : 1;
		texture = block.getBlockTexture(blockAccess, x, y, z, 1);
		renderBlocks.renderTopFace(block, x, y, z, texture);
		renderBlocks.uvRotateTop = 0;

		//

		// south
		texture = block.getBlockTexture(blockAccess, x, y, z, 2);
		renderBlocks.renderEastFace(block, x, y, z, texture);

		// west
		texture = block.getBlockTexture(blockAccess, x, y, z, 3);
		renderBlocks.renderSouthFace(block, x, y, z, texture);

		// north
		texture = block.getBlockTexture(blockAccess, x, y, z, 4);
		renderBlocks.renderWestFace(block, x, y, z, texture);

		// east
		texture = block.getBlockTexture(blockAccess, x, y, z, 5);
		renderBlocks.renderNorthFace(block, x, y, z, texture);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return this.renderId;
	}

	protected void bindTexture(String path) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		mc.renderEngine.bindTexture(mc.renderEngine.getTexture(path));
	}

}
