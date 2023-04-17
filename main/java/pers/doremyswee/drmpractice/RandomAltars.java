package pers.doremyswee.drmpractice;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid =RandomAltars.ID,name=RandomAltars.NAME)
public class RandomAltars {
    public static final String ID="random_altars";
    public static final String NAME="Random Altars";
    public static Logger logger;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Registry.init();
        logger=event.getModLog();
    }
}
