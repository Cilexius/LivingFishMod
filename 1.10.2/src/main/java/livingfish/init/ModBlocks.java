package livingfish.init;

import livingfish.blocks.BlockTank;
import livingfish.blocks.BlockTank_Water;
import livingfish.utils.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block tank;
	public static Block tank_water;
	
	public void init() {
		tank_water = new BlockTank_Water();
		RegistryUtils.setBlockNames(tank_water, "tank_water");
		
		tank = new BlockTank();
		RegistryUtils.setBlockNames(tank, "tank");
	}

	public void register() {
		registerBlock(tank);
		registerBlock(tank_water);
	}

	public void registerBlock(Block block) {
		GameRegistry.register(block);
		ItemBlock itemblock = new ItemBlock(block);
		itemblock.setUnlocalizedName(block.getUnlocalizedName()).setRegistryName(block.getRegistryName());
		GameRegistry.register(itemblock);
	}
	
}
