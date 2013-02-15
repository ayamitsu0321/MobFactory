package ayamitsu.mobfactory;

import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import ayamitsu.mobfactory.conveyor.RenderConveyor;
import ayamitsu.mobfactory.conveyor.RenderConveyorSlope;
import ayamitsu.mobfactory.item.MobItemRenderer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy {

	@Override
	public void preLoad() {

	}

	public void load() {
		MinecraftForgeClient.registerItemRenderer(Loader.instance.itemMob.itemID,new MobItemRenderer());
		RenderingRegistry.registerBlockHandler(new RenderConveyor(Loader.instance.renderConveyorId));
		RenderingRegistry.registerBlockHandler(new RenderConveyorSlope(Loader.instance.renderConveyorSlopeId));
	}

	@Override
	public boolean isClientSide() {
		return true;
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	public int getUniqueBlockModelId() {
		return RenderingRegistry.getNextAvailableRenderId();
	}
}
