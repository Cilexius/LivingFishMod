package livingfish.init;

import livingfish.handlers.ClickEvent;
import livingfish.handlers.WorldEvent;
import net.minecraftforge.common.MinecraftForge;

public class ModHandlers {

	public void register() {
		MinecraftForge.EVENT_BUS.register(new ClickEvent());
		MinecraftForge.EVENT_BUS.register(new WorldEvent());
	}
	
}
