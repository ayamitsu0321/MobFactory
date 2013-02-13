package ayamitsu.mobfactory;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import ayamitsu.mobfactory.item.ItemMob;
import ayamitsu.mobfactory.item.ItemMobTranslator;
import ayamitsu.mobfactory.machine.BlockConveyor;
import ayamitsu.mobfactory.machine.BlockConveyorCorner;
import ayamitsu.mobfactory.machine.BlockConveyorSlope;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class Loader {

	public static Loader instance = new Loader();


	/** blocks */
	public Block conveyor;
	public Block conveyorSlope;
	public Block conveyorCorner;

	/** blocks IDs */
	private int conveyorId;
	private int conveyorSlopeId;
	private int conveyorCornerId;


	/** items */
	public Item itemMob;
	public Item mobTranslator;

	/** items IDs */
	private int itemMobId;
	private int mobTranslatorId;


	/** block render ids, client only */
	public int renderConveyorId;
	public int renderConveyorSlopeId;

	private Loader() {}

	public void preLoad(FMLPreInitializationEvent event) {
		Configuration conf = new Configuration(event.getSuggestedConfigurationFile());
		conf.load();

		this.conveyorId = conf.getBlock("Conveyor", 1230).getInt();
		this.conveyorSlopeId = conf.getBlock("ConveyorSlope", 1231).getInt();
		this.conveyorCornerId = conf.getBlock("ConveyorCorner", 1232).getInt();

		this.itemMobId = conf.getItem("ItemMob", 12300).getInt();
		this.mobTranslatorId = conf.getItem("MobTranslator", 12301).getInt();

		conf.save();

		this.renderConveyorId = MobFactory.proxy.getUniqueBlockModelId();
		this.renderConveyorSlopeId = MobFactory.proxy.getUniqueBlockModelId();
	}

	public void load(FMLInitializationEvent event) {
		this.conveyor = new BlockConveyor(this.conveyorId, Material.iron).setBlockName("mobfactory.conveyor").setCreativeTab(MobFactory.tabMobFactory);
		GameRegistry.registerBlock(this.conveyor, "mobfactory.conveyor");
		this.conveyorSlope = new BlockConveyorSlope(this.conveyorSlopeId, Material.iron).setBlockName("mobfactory.conveyorSlope").setCreativeTab(MobFactory.tabMobFactory);
		GameRegistry.registerBlock(this.conveyorSlope, "mobfactory.conveyorSlope");
		this.conveyorCorner = new BlockConveyorCorner(this.conveyorCornerId, Material.iron).setBlockName("mobfactory.conveyorCorner").setCreativeTab(MobFactory.tabMobFactory);
		GameRegistry.registerBlock(this.conveyorCorner, "mobfactory.conveyorCorner");

		this.itemMob = new ItemMob(this.itemMobId).setItemName("mobfactory.itemMob").setCreativeTab(MobFactory.tabMobFactory);
		this.mobTranslator = new ItemMobTranslator(this.mobTranslatorId).setItemName("mobfactory.mobTranslator").setCreativeTab(MobFactory.tabMobFactory);

		LanguageRegistry.instance().addNameForObject(this.conveyor, "en_US", "Conveyor");
		LanguageRegistry.instance().addNameForObject(this.conveyorSlope, "en_US", "Conveyor Slope");
		LanguageRegistry.instance().addNameForObject(this.conveyorCorner, "en_US", "Conveyor Corner");

		LanguageRegistry.instance().addNameForObject(this.itemMob, "en_US", "Item Mob");
		LanguageRegistry.instance().addNameForObject(this.mobTranslator, "en_US", "Mob Translator");

		// debug
		/*GameRegistry.addRecipe(new ItemStack(this.mobTranslator),
				new Object[] {
			"XXX",
			'X', Block.dirt
		});*/
	}
}
