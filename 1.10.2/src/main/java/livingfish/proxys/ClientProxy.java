package livingfish.proxys;

import livingfish.client.ModelFish;
import livingfish.client.RenderEntityFish;
import livingfish.client.RenderIronFishHook;
import livingfish.entities.EntityClownFish;
import livingfish.entities.EntityCod;
import livingfish.entities.EntityIronFishHook;
import livingfish.entities.EntityPufferFish;
import livingfish.entities.EntitySalmon;
import livingfish.init.ModBlocks;
import livingfish.init.ModItems;
import livingfish.utils.RegistryUtils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	public void registerItemModels() {
		RegistryUtils.registerModel(ModItems.fish_bucket_cod, 0, new ModelResourceLocation(ModItems.fish_bucket_cod.getRegistryName(), "inventory"));
		RegistryUtils.registerModel(ModItems.fish_bucket_salmon, 0, new ModelResourceLocation(ModItems.fish_bucket_salmon.getRegistryName(), "inventory"));
		RegistryUtils.registerModel(ModItems.fish_bucket_clownfish, 0, new ModelResourceLocation(ModItems.fish_bucket_clownfish.getRegistryName(), "inventory"));
		RegistryUtils.registerModel(ModItems.fish_bucket_pufferfish, 0, new ModelResourceLocation(ModItems.fish_bucket_pufferfish.getRegistryName(), "inventory"));
		
		RegistryUtils.registerModel(ModItems.ironhook, 0, new ModelResourceLocation(ModItems.ironhook.getRegistryName(), "inventory"));
		RegistryUtils.registerModel(ModItems.fishbones, 0, new ModelResourceLocation(ModItems.fishbones.getRegistryName(), "inventory"));
		
		RegistryUtils.registerModel(ModItems.fishingrod, 0, new ModelResourceLocation(ModItems.fishingrod.getRegistryName(), "inventory"));
	
		RegistryUtils.registerModel(Item.getItemFromBlock(ModBlocks.tank), 0, new ModelResourceLocation(ModBlocks.tank.getRegistryName(), "inventory"));
		RegistryUtils.registerModel(Item.getItemFromBlock(ModBlocks.tank_water), 0, new ModelResourceLocation(ModBlocks.tank_water.getRegistryName(), "inventory"));
	}
	
	public void registerEntityRendering() {
		RenderingRegistry.registerEntityRenderingHandler(EntityCod.class, new RenderEntityFish(Minecraft.getMinecraft().getRenderManager(), new ModelFish(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySalmon.class, new RenderEntityFish(Minecraft.getMinecraft().getRenderManager(), new ModelFish(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityClownFish.class, new RenderEntityFish(Minecraft.getMinecraft().getRenderManager(), new ModelFish(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityPufferFish.class, new RenderEntityFish(Minecraft.getMinecraft().getRenderManager(), new ModelFish(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityIronFishHook.class, new RenderIronFishHook(Minecraft.getMinecraft().getRenderManager()));
	}
	
	public void setTankModel(){
		ModelLoader.setCustomStateMapper(ModBlocks.tank_water, new StateMap.Builder().ignore(new IProperty[] { BlockLiquid.LEVEL }).build());
	}
	
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
}
