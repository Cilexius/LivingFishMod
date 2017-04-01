package livingfish.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSounds {

	public static SoundEvent rodReel;
	
	public static void register() {
		rodReel = registerSound("livingfish:rodReel");
	}

	private static SoundEvent registerSound(String soundName) {
		ResourceLocation sound = new ResourceLocation(soundName);
        return GameRegistry.register(new SoundEvent(sound).setRegistryName(soundName));
	}
	
}
