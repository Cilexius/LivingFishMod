package livingfish.init;

import livingfish.LivingFish;
import livingfish.network.CToSMessage;
import livingfish.network.PacketHandlerClient;
import livingfish.network.PacketHandlerServer;
import livingfish.network.SToCMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {

    public static SimpleNetworkWrapper networkWrapper;
    
	public void register() {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LivingFish.MODID);
        networkWrapper.registerMessage(PacketHandlerServer.class, CToSMessage.class, 0, Side.SERVER);
        networkWrapper.registerMessage(PacketHandlerClient.class, SToCMessage.class, 1, Side.CLIENT);
	}

}
