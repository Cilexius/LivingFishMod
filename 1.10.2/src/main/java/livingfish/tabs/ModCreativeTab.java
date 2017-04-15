package livingfish.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ModCreativeTab extends CreativeTabs {
	
	public ModCreativeTab() {
		super("fish");
	}
	
	@Override
	public Item getTabIconItem() {
		return Items.FISH;
	}

}
