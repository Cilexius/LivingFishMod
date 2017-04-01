package livingfish.init;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfigs {
	
	public static int maxFishPerPlayer;
	public static int safetyDistance;
	public static int range;
	public static int timerAtDay;
	public static int timerAtNight;

	public static int codWeight;
	public static int codMin;
	public static int codMax;
	
	public static int salmonWeight;
	public static int salmonMin;
	public static int salmonMax;
	
	public static int clownfishWeight;
	public static int clownfishMin;
	public static int clownfishMax;
	
	public static int pufferfishWeight;
	public static int pufferfishMin;
	public static int pufferfishMax;
	
	public void register(FMLPreInitializationEvent event) {
		
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	String commentWeight = "Sets the weight for each spawn attempt. => The value is relative to all fish weight values.";
    	String commentMin = "Sets group spawn minimum  (must not be greater than Max)";
    	String commentMax = "Sets group spawn maximum  (must be less than Min)";

    	
    	config.load();
    	
    	this.generalSpawning(config);
    	this.cod(config, commentWeight, commentMin, commentMax);
    	this.salmon(config, commentWeight, commentMin, commentMax);
    	this.clownfish(config, commentWeight, commentMin, commentMax);
    	this.pufferfish(config, commentWeight, commentMin, commentMax);
    	
    	config.save();
	}
	
	public static void generalSpawning(Configuration config) {
		maxFishPerPlayer = config.getInt("maxFishPerPlayer", "generalspawning", 60, 1, 1000, "Sets the amount, at which spawn methode stops spawning fish. This is done for each player");
		safetyDistance = config.getInt("spawnSafetyDistance", "generalspawning", 16, 0, 1000, "Sets a safety distance around the player, at which fish can not be spawned.  (value of 1 = length of one block)");
		range = config.getInt("spawnRange", "generalspawning", 32, 20, 1016, "Sets a limit, for how far away from the player, fish can be spawned.  (value of 1 = length of one block)");
		timerAtDay = config.getInt("timerAtDay", "generalspawning", 4, 0, 10000, "Sets time between spawn attempts at day time in ticks. To disable spawns at day, set to -1.  (20 ticks = 1s)");
		timerAtNight = config.getInt("timerAtNight", "generalspawning", 20, 0, 10000, "Sets time between spawn attempts at nigh time in ticks. To disable spawns at night, set to -1.  (20 ticks = 1s)");
	}
	
	public static void cod(Configuration config, String commentWeight, String commentMin, String commentMax) {
		codWeight = config.getInt("codWeight", "cod", 50, 0, 1000, commentWeight);
		codMin = config.getInt("codMin", "cod", 1, 1, 20, commentMin);
		codMax = config.getInt("codMax", "cod", 4, 1, 20, commentMax);
	}
	
	public static void salmon(Configuration config, String commentWeight, String commentMin, String commentMax) {
		salmonWeight = config.getInt("salmonWeight", "salmon", 35, 0, 1000, commentWeight);
		salmonMin = config.getInt("salmonMin", "salmon", 1, 1, 20, commentMin);
		salmonMax = config.getInt("salmonMax", "salmon", 4, 1, 20, commentMax);
	}
	
	public static void clownfish(Configuration config, String commentWeight, String commentMin, String commentMax) {
		clownfishWeight = config.getInt("clownfishWeight", "clownfish", 9, 0, 1000, commentWeight);
		clownfishMin = config.getInt("clownFishMin", "clownfish", 1, 1, 20, commentMin);
		clownfishMax = config.getInt("clownFishMax", "clownfish", 2, 1, 20, commentMax);
	}
	
	public static void pufferfish(Configuration config, String commentWeight, String commentMin, String commentMax) {
		pufferfishWeight = config.getInt("pufferfishWeight", "pufferfish", 6, 0, 1000, commentWeight);
		pufferfishMin = config.getInt("pufferFishMin", "pufferfish", 1, 1, 20, commentMin);
		pufferfishMax = config.getInt("pufferFishMax", "pufferfish", 2, 1, 20, commentMax);
	}

}
