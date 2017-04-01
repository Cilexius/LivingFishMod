package livingfish.init;

import livingfish.blocks.BlockTank;
import livingfish.utils.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block tank;
	
	public void init() {
		tank = new BlockTank();
		RegistryUtils.setBlockNames(tank, "tank");
	}

	public void register() {
		registerBlock(tank);
	}

	public void registerBlock(Block block) {
		GameRegistry.register(block);
		ItemBlock itemblock = new ItemBlock(block);
		itemblock.setUnlocalizedName(block.getUnlocalizedName()).setRegistryName(block.getRegistryName());
		GameRegistry.register(itemblock);
	}
	
}
