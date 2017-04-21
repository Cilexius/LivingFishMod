package livingfish.init;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfigs {
	
	public static int maxFishPerPlayer;
	public static int safetyDistance;
	public static int range;
	public static int timerAtDay;
	public static int timerAtNight;

	public static int codMaxHealth;
	public static int codWeight;
	public static int codMin;
	public static int codMax;
	
	public static int salmonMaxHealth;
	public static int salmonWeight;
	public static int salmonMin;
	public static int salmonMax;
	
	public static int clownfishMaxHealth;
	public static int clownfishWeight;
	public static int clownfishMin;
	public static int clownfishMax;
	
	public static int pufferfishMaxHealth;
	public static int pufferfishWeight;
	public static int pufferfishMin;
	public static int pufferfishMax;
	public static int pufferfishAddExtraScaredTime;
	public static int pufferfishRandomScared;
	
	public static int fishbonesChance;
	
	public static boolean dropTank;
	public static boolean looseWaterOnDestruction;
	
	public static boolean changeHookRecipe;
	public static boolean sneakClickHookRecipe;
	
	public void register(FMLPreInitializationEvent event) {
		
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	String commentHealth = "Sets maximum health of the entity.  (Value of 1 = 1 Heart)";
    	String commentWeight = "Sets the weight for each spawn attempt. => The value is relative to all fish weight values.";
    	String commentMin = "Sets group spawn minimum.  (must not be greater than Max)";
    	String commentMax = "Sets group spawn maximum.  (must be less than Min)";

    	
    	config.load();
    	
    	this.generalSpawning(config);
    	this.cod(config, commentHealth, commentWeight, commentMin, commentMax);
    	this.salmon(config, commentHealth, commentWeight, commentMin, commentMax);
    	this.clownfish(config, commentHealth, commentWeight, commentMin, commentMax);
    	this.pufferfish(config, commentHealth, commentWeight, commentMin, commentMax);
    	this.fishing(config);
    	this.tankConfig(config);
    	this.recipeConfig(config);
    	
    	config.save();
	}
	
	public static void generalSpawning(Configuration config) {
		maxFishPerPlayer = config.getInt("maxFishPerPlayer", "generalspawning", 60, 1, 1000, "Sets the amount, at which spawn methode stops spawning fish. This is done for each player");
		safetyDistance = config.getInt("spawnSafetyDistance", "generalspawning", 16, 0, 1000, "Sets a safety distance around the player, at which fish can not be spawned.  (value of 1 = length of one block)");
		range = config.getInt("spawnRange", "generalspawning", 32, 20, 1016, "Sets a limit, for how far away from the player, fish can be spawned.  (value of 1 = length of one block)");
		timerAtDay = config.getInt("timerAtDay", "generalspawning", 4, 0, 10000, "Sets time between spawn attempts at day time in ticks. To disable spawns at day, set to -1.  (20 ticks = 1s)");
		timerAtNight = config.getInt("timerAtNight", "generalspawning", 20, 0, 10000, "Sets time between spawn attempts at nigh time in ticks. To disable spawns at night, set to -1.  (20 ticks = 1s)");
	}
	
	public static void cod(Configuration config, String commentHealth, String commentWeight, String commentMin, String commentMax) {
		codMaxHealth = config.getInt("codMaxHealth", "cod", 8, 1, 20, commentHealth);
		codWeight = config.getInt("codWeight", "cod", 50, 0, 1000, commentWeight);
		codMin = config.getInt("codMin", "cod", 1, 1, 20, commentMin);
		codMax = config.getInt("codMax", "cod", 4, 1, 20, commentMax);
	}
	
	public static void salmon(Configuration config, String commentHealth, String commentWeight, String commentMin, String commentMax) {
		salmonMaxHealth = config.getInt("salmonMaxHealth", "salmon", 10, 1, 20, commentHealth);
		salmonWeight = config.getInt("salmonWeight", "salmon", 35, 0, 1000, commentWeight);
		salmonMin = config.getInt("salmonMin", "salmon", 1, 1, 20, commentMin);
		salmonMax = config.getInt("salmonMax", "salmon", 4, 1, 20, commentMax);
	}
	
	public static void clownfish(Configuration config, String commentHealth, String commentWeight, String commentMin, String commentMax) {
		clownfishMaxHealth = config.getInt("clownfishMaxHealth", "clownfish", 4, 1, 20, commentHealth);
		clownfishWeight = config.getInt("clownfishWeight", "clownfish", 9, 0, 1000, commentWeight);
		clownfishMin = config.getInt("clownFishMin", "clownfish", 1, 1, 20, commentMin);
		clownfishMax = config.getInt("clownFishMax", "clownfish", 2, 1, 20, commentMax);
	}
	
	public static void pufferfish(Configuration config, String commentHealth, String commentWeight, String commentMin, String commentMax) {
		pufferfishMaxHealth = config.getInt("pufferfishMaxHealth", "pufferfish", 16, 1, 20, commentHealth);
		pufferfishWeight = config.getInt("pufferfishWeight", "pufferfish", 6, 0, 1000, commentWeight);
		pufferfishMin = config.getInt("pufferFishMin", "pufferfish", 1, 1, 20, commentMin);
		pufferfishMax = config.getInt("pufferFishMax", "pufferfish", 2, 1, 20, commentMax);
		pufferfishAddExtraScaredTime = config.getInt("pufferfishAddExtraScaredTime", "pufferfish", 0, 1, 10000, "Add extra time to the scaredtime of the Pufferifsh.  (Value of 20 = 1 sec)");
		pufferfishRandomScared = config.getInt("pufferfishRandomScared", "pufferfish", 10000, 1, 1000000, "Change the probability, with which the Pufferfish will randomly inflate itself. Greater values will make random inflation less probable.  (Value of 20 = 1 sec)");
	}
	
	public static void fishing(Configuration config) {
		fishbonesChance = config.getInt("fishbonesChance", "fishing", 7000, -1, 10000000, "Sets the chance for fishing fishbones, when no fish has bitten. To disable, set to -1.  (As greater the number as smaler the chance)");
	}
	
	public static void tankConfig(Configuration config) {
		dropTank = config.getBoolean("dropAquarium", "aquarium", false, "Set this to true, if you want the aquarium to be droped instead of being destroyed");
		looseWaterOnDestruction = config.getBoolean("looseWaterOnDestruction", "aquarium", true, "Set this to false, if you want the aquarium not to loose its content on destruction");
	}
	
	public static void recipeConfig(Configuration config) {
		changeHookRecipe = config.getBoolean("changeHookRecipe", "recipes", false, "Set this to true, to change the recipe for the iron hook. => The recipe will change to TWO iron ingots and FOUR hooks.");
		sneakClickHookRecipe = config.getBoolean("sneakClickHookRecipe", "recipes", false, "Set this to true, to enable the 'Sneak + Right Click' Recipe for the iron hook");
	}

}
