package livingfish.init;

import livingfish.handlers.BucketEvent;
import livingfish.handlers.WorldEvent;
import net.minecraftforge.common.MinecraftForge;

public class ModHandlers {

	public void register() {
		MinecraftForge.EVENT_BUS.register(new BucketEvent());
		MinecraftForge.EVENT_BUS.register(new WorldEvent());
	}
	
}
