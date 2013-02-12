package ayamitsu.mobfactory.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({ "ayamitsu.mobfactory.asm" })
public class MobFactoryCorePlugin implements IFMLLoadingPlugin {

	static File location;

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
			"ayamitsu.mobfactory.asm.EntityTransformer"
		};
	}

	@Override
	public String getModContainerClass() {
		return "ayamitsu.mobfactory.asm.MobFactoryModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if (data.containsKey("coremodLocation")) {
			location = (File)data.get("coremodLocation");
		}
	}

}
