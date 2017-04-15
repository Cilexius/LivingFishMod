package livingfish.init;

import livingfish.LivingFish;
import livingfish.entities.EntityClownFish;
import livingfish.entities.EntityCod;
import livingfish.entities.EntityIronFishHook;
import livingfish.entities.EntityPufferFish;
import livingfish.entities.EntitySalmon;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public void register() {
		EntityRegistry.registerModEntity(EntityCod.class, "cod", 0, LivingFish.instance, 80, 1, true, 0x6B9F93, 0x6E798B);
		EntityRegistry.registerModEntity(EntitySalmon.class, "salmon", 1, LivingFish.instance, 80, 1, true, 0xAB3533, 0x47594B);
		EntityRegistry.registerModEntity(EntityClownFish.class, "clownfish", 2, LivingFish.instance, 80, 1, true, 0xF46F20, 0x613B23);
		EntityRegistry.registerModEntity(EntityPufferFish.class, "pufferfish", 3, LivingFish.instance, 80, 1, true, 0xEBDE39, 0x429BBA);
		
		EntityRegistry.registerModEntity(EntityIronFishHook.class, "ironfishhook", 10, LivingFish.instance, 80, 1, true);
	}
	
}
