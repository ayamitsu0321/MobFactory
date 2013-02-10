package ayamitsu.mobfactory;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
	modid = "MobFactory",
	name = "MobFactory",
	version = "0.0.1"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false,
	channels = { "mobfactory" },
	packetHandler = ayamitsu.mobfactory.network.PacketHandler.class
)
public class MobFactory {

	@Mod.Instance("MobFactory")
	public static MobFactory instance;

	@SidedProxy(clientSide = "ayamitsu.mobfactory.ClientProxy", serverSide = "ayamitsu.mobfactory.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tabMobFactory = new CreativeTabMobFactory("MobFactory");


	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Loader.instance.preLoad(event);
		this.proxy.preLoad();
	}

	@Mod.Init
	public void init(FMLInitializationEvent event) {
		Loader.instance.load(event);
		this.proxy.load();
	}

	public static class CreativeTabMobFactory extends CreativeTabs {
		public CreativeTabMobFactory(String name) {
			super(name);
		}
	}
}
