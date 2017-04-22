package livingfish;

import livingfish.init.ModBlocks;
import livingfish.init.ModConfigs;
import livingfish.init.ModEntities;
import livingfish.init.ModHandlers;
import livingfish.init.ModItems;
import livingfish.init.ModNetwork;
import livingfish.init.ModSounds;
import livingfish.items.recipes.ModCrafting;
import livingfish.items.recipes.ModSmelting;
import livingfish.proxys.CommonProxy;
import livingfish.tabs.ModCreativeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LivingFish.MODID, name = LivingFish.NAME, version = LivingFish.VERSION)
public class LivingFish {
	
    public static final String MODID = "livingfish";
    public static final String NAME = "LivingFish";
    public static final String VERSION = "V3";
    
    @Instance(MODID)
    public static LivingFish instance = new LivingFish();
    
    @SidedProxy(modId = MODID, serverSide = "livingfish.proxys.CommonProxy", clientSide = "livingfish.proxys.ClientProxy")
    public static CommonProxy proxy = new CommonProxy();
    
    public ModConfigs configs;
    public ModCreativeTab tab;
    public ModItems items;
    public ModBlocks blocks;
    public ModEntities entities;
    public ModSounds sounds;
    public ModNetwork network;
    public ModCrafting crafting;
    public ModSmelting smelting;
    public ModHandlers handlers;
    
    @EventHandler
    public void init(FMLPreInitializationEvent event) {
    	configs = new ModConfigs();
    	configs.register(event);
    	tab = new ModCreativeTab();
    	items = new ModItems();
    	items.init();
    	items.register();
    	blocks = new ModBlocks();
    	blocks.init();
    	blocks.register();
    	entities = new ModEntities();
    	entities.register();
    	sounds = new ModSounds();
    	sounds.register();
    	network = new ModNetwork();
    	network.register();
    	proxy.setTankModel();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	crafting = new ModCrafting();
    	crafting.register();
    	smelting = new ModSmelting();
    	smelting.register();
    	handlers = new ModHandlers();
    	handlers.register();
    	proxy.registerItemModels();
    	proxy.registerEntityRendering();
    }
    
    @EventHandler
    public void init(FMLPostInitializationEvent event) {
    }
    
}
