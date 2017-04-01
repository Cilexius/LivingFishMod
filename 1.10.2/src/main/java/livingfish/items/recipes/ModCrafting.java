package livingfish.items.recipes;

import livingfish.init.ModBlocks;
import livingfish.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	
	public void register() {		
		//Shapeless
    	GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ironhook, 2), Items.IRON_INGOT);
    	GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.tank, 1), new ItemStack(Blocks.GLASS), Items.WATER_BUCKET);

		//Shaped
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.fishingrod, 1), new Object[] {
				"  x",
				" xs",
				"x h", 'x', new ItemStack(Items.STICK), 's', new ItemStack(Items.STRING), 'h', new ItemStack(ModItems.ironhook)
		});
		
	}
	
}
