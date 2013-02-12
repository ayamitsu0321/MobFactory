package ayamitsu.mobfactory.asm;

import java.util.Arrays;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;

public class MobFactoryModContainer extends DummyModContainer {

	public MobFactoryModContainer() {
		super(new ModMetadata());
		ModMetadata meta = this.getMetadata();
		meta.modId       = "mobfactoryplugin";
		meta.name        = "MobFactoryPlugin";
		meta.version     = "0.0.1";
		meta.authorList  = Arrays.asList("ayamitsu");
		meta.description = "";
		meta.url         = "";
		meta.credits     = "";
	}
}
