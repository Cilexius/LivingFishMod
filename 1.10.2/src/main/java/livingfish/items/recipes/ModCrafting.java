package livingfish.items.recipes;

import livingfish.init.ModBlocks;
import livingfish.init.ModConfigs;
import livingfish.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	
	public void register() {		
		//Shapeless
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 15), ModItems.fishbones);
    	this.hookRecipe();
    	GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tank, 1), new ItemStack(Blocks.GLASS));
    	GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tank_water, 1), new ItemStack(ModBlocks.tank), Items.WATER_BUCKET);

		//Shaped
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.fishingrod, 1), new Object[] {
				"  x",
				" xs",
				"x h", 'x', new ItemStack(Items.STICK), 's', new ItemStack(Items.STRING), 'h', new ItemStack(ModItems.ironhook)
		});
		
	}
	
	public void hookRecipe() {
		if (ModConfigs.changeHookRecipe) {
	    	GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ironhook, 4), Items.IRON_INGOT, Items.IRON_INGOT);
		} else {
	    	GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ironhook, 2), Items.IRON_INGOT);
		}
	}
	
}
