package ayamitsu.mobfactory.machine;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderConveyorSlope implements ISimpleBlockRenderingHandler {

	private int renderId;

	public RenderConveyorSlope(int render) {
		this.renderId = render;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderBlocks) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public int getRenderId() {
		return this.renderId;
	}

}
