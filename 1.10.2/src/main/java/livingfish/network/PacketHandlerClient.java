package livingfish.network;

import io.netty.buffer.Unpooled;
import livingfish.LivingFish;
import livingfish.entities.EntityIronFishHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerClient implements IMessageHandler<SToCMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SToCMessage message, MessageContext ctx) {
		
		PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(message.getData()));
		
		EntityPlayer player = LivingFish.proxy.getPlayer();
		World world = null;
		int hookID = 0;
		if (player != null) {
			world = player.worldObj;
		}
		
		int type = buf.readInt();
		switch (type)
		{
		case 0:
			int playerID = buf.readInt();
			hookID = buf.readInt();
			if (world != null) {
				
				if (player.getEntityId() == playerID) {
					for (Entity entity : world.loadedEntityList) {
						if (entity instanceof EntityIronFishHook) {
							if (entity.getEntityId() == hookID) {
								player.fishEntity = (EntityFishHook) entity;
							}
						}
					}
				}
				
				for (Entity entity : world.loadedEntityList) {
					if (entity instanceof EntityIronFishHook) {
						if (entity.getEntityId() == hookID) {
							((EntityIronFishHook) entity).angler = (EntityPlayer) world.getEntityByID(playerID);
						}
					}
				}
				
			}
			break;
		case 1:
			hookID = buf.readInt();
			if (player.fishEntity != null && player.fishEntity.getEntityId() == hookID) {
				player.fishEntity.setDead();
				player.fishEntity = null;
			}
			break;
		}
	    return null;
	}
	  
}