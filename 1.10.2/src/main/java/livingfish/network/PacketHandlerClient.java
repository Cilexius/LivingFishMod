package livingfish.network;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerClient implements IMessageHandler<SToCMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SToCMessage message, MessageContext ctx) {
		
		PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(message.getData()));
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		int type = buf.readInt();
		switch (type)
		{
		case 1:
			player.fishEntity = null;
			break;
		}
	    return null;
	}
	  
}