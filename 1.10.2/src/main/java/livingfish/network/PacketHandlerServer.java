package livingfish.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerServer implements IMessageHandler<CToSMessage, IMessage> {

	@Override
	public IMessage onMessage(CToSMessage message, MessageContext ctx) {
		
		ByteBuf buff = Unpooled.wrappedBuffer(message.getData());
		
	    int type = buff.readInt();
	    switch (type)
	    {
		case 1:
			
			break;
		}
	    return null;
	}

}
