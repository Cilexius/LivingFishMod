package livingfish.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RegistryUtils {
	
	public static void registerModel(Item item, int meta, ModelResourceLocation loc) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, loc);
	}
	
	public static void setItemNames(Item item, String name) {
		item.setRegistryName(name).setUnlocalizedName(name);
	}
	
	public static void setBlockNames(Block block, String name) {
		block.setRegistryName(name).setUnlocalizedName(name);
	}
	
}
