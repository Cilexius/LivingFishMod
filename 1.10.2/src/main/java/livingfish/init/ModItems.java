package livingfish.init;

import livingfish.items.Fishingrod;
import livingfish.items.ItemFishBucket;
import livingfish.utils.RegistryUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
		
	public static Item fish_bucket_cod;
	public static Item fish_bucket_salmon;
	public static Item fish_bucket_clownfish;
	public static Item fish_bucket_pufferfish;
	
	public static Item ironhook;
	
	public static Item fishingrod;
	
	public void init() {
		fish_bucket_cod = new ItemFishBucket("cod");
		RegistryUtils.setItemNames(fish_bucket_cod, "fish_bucket_cod");
		fish_bucket_cod = new ItemFishBucket("cod");
		RegistryUtils.setItemNames(fish_bucket_cod, "fish_bucket_cod");
		fish_bucket_salmon = new ItemFishBucket("salmon");
		RegistryUtils.setItemNames(fish_bucket_salmon, "fish_bucket_salmon");
		fish_bucket_clownfish = new ItemFishBucket("clownfish");
		RegistryUtils.setItemNames(fish_bucket_clownfish, "fish_bucket_clownfish");
		fish_bucket_pufferfish = new ItemFishBucket("pufferfish");
		RegistryUtils.setItemNames(fish_bucket_pufferfish, "fish_bucket_pufferfish");
		
		ironhook = new Item().setCreativeTab(CreativeTabs.MISC);
		RegistryUtils.setItemNames(ironhook, "ironhook");
		
		fishingrod = new Fishingrod().setCreativeTab(CreativeTabs.TOOLS);
		RegistryUtils.setItemNames(fishingrod, "fishingrod");
	}
	
	public void register() {
		registerItem(fish_bucket_cod);
		registerItem(fish_bucket_salmon);
		registerItem(fish_bucket_clownfish);
		registerItem(fish_bucket_pufferfish);
		
		registerItem(ironhook);
		
		registerItem(fishingrod);
	}
	
	private void registerItem(Item item) {
		GameRegistry.register(item);
	}
	
}
