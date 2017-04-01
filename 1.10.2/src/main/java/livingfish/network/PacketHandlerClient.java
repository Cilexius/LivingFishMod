package livingfish.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerClient implements IMessageHandler<SToCMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SToCMessage message, MessageContext ctx) {
		
		PacketBuffer buf = new PacketBuffer(Unpooled.wrappedBuffer(message.getData()));
		
		//EntityPlayer player = ctx.getServerHandler().playerEntity;
		
		int type = buf.readInt();
		switch (type)
		{
		case 1:
			//My Cod here
			break;
		}
	    return null;
	}
	  
}